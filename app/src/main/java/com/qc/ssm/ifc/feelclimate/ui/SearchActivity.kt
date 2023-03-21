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
    private lateinit var viewModel: ClimateViewModel
    private lateinit var binding: ActivitySearchBinding
    private lateinit var popularAdapter: PopularAdapter;
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

        viewModel.climateDatum.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<ClimateModel> -> {
                    populateView(dataState.data)
                }
                is DataState.Loading -> {
                }
                is DataState.Error -> {
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

    private fun splitCitiesFromHistory(): Array<String>? {
        PreferenceData.init(this@SearchActivity);
        val list: Array<String>? = PreferenceData.read()?.split(",".toRegex())?.toTypedArray()
        if (list != null && list.isNotEmpty()) {
            return list;
        }
        return null;
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
            val list = splitCitiesFromHistory()
            var needToSave = ArrayList<String>()
            list?.let {
                for (i in it) {
                    if (!i.equals(data.name) && i.isNotEmpty())
                        needToSave.add("$i,")
                }
            }
            needToSave.add("${data.name},")
            var builder = java.lang.StringBuilder();
            var count = 0
            for (i in needToSave) {
                if (count < 10) {
                    builder.append(i.trim())
                }
                count++
            }
            PreferenceData.writeHistory(builder.toString())

            val ft = supportFragmentManager.beginTransaction()
            ft.replace(binding.containerFragment.getId(), ClimateFragment.newInstance(data))
            ft.addToBackStack(null).commit()
            popularAdapter.setData(splitCitiesFromHistory());
        }
    }

    private fun setPopularListing() {
        binding.popularRec.layoutManager =
            StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
        popularAdapter = PopularAdapter(this@SearchActivity, listener, splitCitiesFromHistory())
        binding.popularRec.adapter = popularAdapter;
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