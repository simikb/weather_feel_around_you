package com.qc.ssm.ifc.feelclimate.ui

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.qc.ssm.ifc.feelclimate.adapter.PopularAdapter
import com.qc.ssm.ifc.feelclimate.databinding.ActivitySearchBinding
import com.qc.ssm.ifc.feelclimate.databinding.DialogOptionLayoutBinding
import com.qc.ssm.ifc.feelclimate.models.ClimateModel
import com.qc.ssm.ifc.feelclimate.utils.DataState
import com.qc.ssm.ifc.feelclimate.utils.LocationDetails
import com.qc.ssm.ifc.feelclimate.utils.Permissions
import com.qc.ssm.ifc.feelclimate.utils.PreferenceData
import com.qc.ssm.ifc.feelclimate.viewmodels.ClimateViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: ClimateViewModel //by viewModels();
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ClimateViewModel::class.java]
        setPopularListing()
        subscribeObservers()
        saveLocation()
        clicks()
        lastSearchDisplay()
    }

    private fun lastSearchDisplay() {
        if (PreferenceData.readLast()?.isNotEmpty() == true) {
            PreferenceData.readLast()?.let { viewModel.getClimateUpdateByPlaceNames(it); }
        } else
            showOptionDialog(this@SearchActivity)
    }

    private fun clicks() {
        binding.searchText.setOnEditorActionListener(OnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                var name = binding.searchText.text.toString().trim()
                if (name.isNotEmpty())
                    viewModel.getClimateUpdateByPlaceNames(name);
                true
            }
            false
        })
        binding.cancelButton.setOnClickListener {
            binding.searchText.setText("")
        }

        binding.locationText.setOnClickListener {
            showOptionDialog(this@SearchActivity)
            if (!Permissions.checkPermission(this@SearchActivity)) {
                viewModel.setError("Please give permission to fetch location")
            }
        }

    }

    private fun saveLocation() {
        LocationDetails(this@SearchActivity).initCall()
    }

    private fun subscribeObservers() {
        /* viewModel.climateDatum.observe(this, Observer { dataState ->
             populateRecyclerView(dataState.data)
         })*/

        viewModel.climateDatum.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<ClimateModel> -> {
                    // displayLoading(false)
                    populateView(dataState.data)
                }
                is DataState.Loading -> {
                    //displayLoading(true)
                }
                is DataState.Error -> {
                    //displayLoading(false)
                    displayError(dataState.exception.message)
                }
            }
        })

        viewModel.getClimateSingleData.observe(this, Observer {
            if (it != null)
                populateView(it)
            else viewModel.setError("No response found for this search")

        })
        viewModel.errorResponse.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

    }

    private fun displayError(message: String?) {
        if (message.isNullOrEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun populateView(data: ClimateModel) {
        if (data != null) {
            PreferenceData.writeLastQuery(data.name)
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(binding.containerFragment.getId(), ClimateFragment.newInstance(data))
            ft.addToBackStack(null).commit()

        }
    }

    private fun setPopularListing() {
        binding.popularRec.layoutManager =
            StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
        binding.popularRec.adapter = PopularAdapter(this@SearchActivity, listener)
    }

    private fun showOptionDialog(context: Context) {
        if (LocationDetails(this@SearchActivity).checkPermissions()) {
            //if (LocationDetails.mCurrentLocation!=null) {
            val dialog = BottomSheetDialog(context)
            var layout = DialogOptionLayoutBinding.inflate(layoutInflater)
            dialog.setContentView(layout.root)
            dialog.setTitle("How you wants to searching?")
            layout.currentLocationButton.setOnClickListener {
                LocationDetails(this@SearchActivity).initCall();
                var locationDetails = LocationDetails(context);
                LocationDetails.mCurrentLocation.let {
                    var lat = LocationDetails.mCurrentLocation?.latitude
                    var lon = LocationDetails.mCurrentLocation?.longitude
                    if (lat != null && lat > 0 && lon != null && lon > 0) {
                        viewModel.getClimateUpdates(lat.toString(), lon.toString());
                        dialog.dismiss()
                    }
                }
            }
            dialog.show()
        }

    }

    private var listener = object : OnItemClickListener {
        override fun onItemClick(item: String?) {
            item?.let { viewModel.getClimateUpdateByPlaceNames(it) };
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: String?)
    }
}