package com.qc.ssm.ifc.feelclimate.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.qc.ssm.ifc.feelclimate.adapter.PopularAdapter
import com.qc.ssm.ifc.feelclimate.databinding.ActivitySearchBinding
import com.qc.ssm.ifc.feelclimate.databinding.DialogOptionLayoutBinding
import com.qc.ssm.ifc.feelclimate.models.ClimateModel
import com.qc.ssm.ifc.feelclimate.utils.DataState
import com.qc.ssm.ifc.feelclimate.utils.LocationDetails
import com.qc.ssm.ifc.feelclimate.viewmodels.MainStateEvent
import com.qc.ssm.ifc.feelclimate.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showOptionDialog()
        setPopularListing()
        subscribeObservers()
        saveLocation();
    }

    private fun saveLocation() {
        LocationDetails(this@SearchActivity).initCall()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<ClimateModel> -> {
                    // displayLoading(false)
                    populateView(dataState.data)
                }
                is DataState.Loading -> {
                    // displayLoading(true)
                }
                is DataState.Error -> {
                    //   displayLoading(false)
                    displayError(dataState.exception.message)
                }
            }
        })
    }

    private fun displayError(message: String?) {
        if (message.isNullOrEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Unknown error", Toast.LENGTH_LONG).show()
        }
    }

    /* private fun displayLoading(isLoading: Boolean) {
         swipeRefreshLayout.isRefreshing = isLoading
     }*/

    private fun populateView(data: ClimateModel) {
        if (data != null) {
        }
    }

    private fun setPopularListing() {
        binding.popularRec.layoutManager =
            StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
        binding.popularRec.adapter = PopularAdapter(this@SearchActivity)
    }

    private fun showOptionDialog() {
        val dialog = BottomSheetDialog(this@SearchActivity)
        var layout = DialogOptionLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(layout.root)
        dialog.setTitle("How you wants to searching?")
        layout.currentLocationButton.setOnClickListener {
            viewModel.setStateEvent(MainStateEvent.GetClimateEvents)
        }
        dialog.show()
    }


}