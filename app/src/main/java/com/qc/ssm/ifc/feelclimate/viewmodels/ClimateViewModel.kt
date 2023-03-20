package com.qc.ssm.ifc.feelclimate.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qc.ssm.ifc.feelclimate.models.ClimateModel
import com.qc.ssm.ifc.feelclimate.repository.MainRepository
import com.qc.ssm.ifc.feelclimate.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ClimateViewModel @Inject constructor (var mainRepository: MainRepository):ViewModel(){
    private val climateData: MutableLiveData<DataState<ClimateModel>> = MutableLiveData()
    private val climateSingleData: MutableLiveData<ClimateModel> = MutableLiveData()
    private val mutableClimateData: MutableLiveData<ClimateModel> = MutableLiveData()
    private val error: MutableLiveData<String> = MutableLiveData()
    val errorResponse: LiveData<String>
        get() = error
    val climateDatum: LiveData<DataState<ClimateModel>>
        get() = climateData
    val getClimateSingleData: LiveData<ClimateModel>
        get() = climateSingleData
    fun getClimateUpdate(lat: String, lon: String) {
        GlobalScope.launch(Dispatchers.IO) {

               /*var response=*/ mainRepository.getClimateByLocation(
                    lat,
                    lon
                )
                    .onEach { dataState ->
                        climateData.value = dataState
                    }
                    .launchIn(viewModelScope)
           // mutableClimateData.value=response.value;
            }
    }

    fun getClimateUpdateByPlaceName(name: String) {
        GlobalScope.launch(Dispatchers.IO) {

            /*var response=*/ mainRepository.getClimate(name)
            .onEach { dataState ->
                climateData.value = dataState
            }
            .launchIn(viewModelScope)
            // mutableClimateData.value=response.value;
        }


    }

    fun getClimateUpdates(lat: String, lon: String) {
        GlobalScope.launch(Dispatchers.IO) {

            var response= mainRepository.getClimateByLocations(lat, lon)
            response.enqueue(object :Callback<ClimateModel>{
                override fun onResponse(
                    call: Call<ClimateModel>,
                    response: Response<ClimateModel>
                ) {
                    Log.e("network_response",response.body().toString())
                   // var data:DataState<ClimateModel> ;
                    var data =DataState.Success(response.body())!!
                    climateSingleData.value = response.body()
                }

                override fun onFailure(call: Call<ClimateModel>, t: Throwable) {
                    Log.e("network_error",t.message.toString())
                    error.value="NetWork error"
                }
            })

        }


    }

    fun getClimateUpdateByPlaceNames(name: String) {
        GlobalScope.launch(Dispatchers.IO) {
            var response= mainRepository.getClimates(name)
            response.enqueue(object :Callback<ClimateModel>{
                override fun onResponse(
                    call: Call<ClimateModel>,
                    response: Response<ClimateModel>
                ) {
                    Log.e("network_response",response.body().toString())
                    // var data:DataState<ClimateModel> ;
                    var data =DataState.Success(response.body())!!
                    climateSingleData.value = response.body()
                }

                override fun onFailure(call: Call<ClimateModel>, t: Throwable) {
                    Log.e("network_error",t.message.toString())
                    error.value="NetWork error"
                }
            })
        }
    }
}