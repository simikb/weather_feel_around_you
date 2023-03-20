package com.qc.ssm.ifc.feelclimate.repository


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qc.ssm.ifc.feelclimate.models.ClimateModel
import com.qc.ssm.ifc.feelclimate.network.ClimateApi
import com.qc.ssm.ifc.feelclimate.utils.DataState
import com.qc.ssm.ifc.feelclimate.utils.Permissions
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository
@Inject
constructor(
    private val climateApi: ClimateApi,
) {
    suspend fun getClimate(query: String): Flow<DataState<ClimateModel>> = flow {
        emit(DataState.Loading)
       // delay(1000)
        try {
            try
            {   val networkBlogs = climateApi.get(query, Permissions.apiKey)
                emit(DataState.Success(networkBlogs))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
           /* val networkClimate = climateApi.get(query, Permissions.apiKey)
            networkClimate.enqueue(object:Callback<ClimateModel>{
                override fun onResponse(
                    call: Call<ClimateModel>,
                    response: Response<ClimateModel>
                ) {
                    Log.e("network_response",response.body().toString())
                  //  saveData(response.body());
                    TODO("Not yet implemented")
                }

                override fun onFailure(call: Call<ClimateModel>, t: Throwable) {
                   Log.e("network_error",t.message.toString())
                }
            })*/
            /* val blogs = climateMapper.mapFromEntityList(networkBlogs)*/
            /*  for (blog in blogs) {
                  blogDao.insert(cacheMapper.mapToEntity(blog))
              }
              val cachedBlogs = blogDao.get()*/
            //emit(DataState.Success(networkClimate))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    private fun saveData(body: ClimateModel?) {
        DataState.Success(body)
    }

    suspend fun getClimateByLocation(lat:String,lon:String): Flow<DataState<ClimateModel>> = flow {
    //{

                try
                {   val networkBlogs = climateApi.getByLocation(lat, lon, Permissions.apiKey)
                    emit(DataState.Success(networkBlogs))
                } catch (e: Exception) {
                    emit(DataState.Error(e))
                }
           /* val mutableLiveData = MutableLiveData<ClimateModel>()
                 val networkClimate = climateApi.getByLocations(lat,lon, Permissions.apiKey)
                networkClimate.enqueue(object:Callback<ClimateModel>{
                    override fun onResponse(
                        call: Call<ClimateModel>,
                        response: Response<ClimateModel>
                    ) {
                        Log.e("network_response",response.body().toString())
                        saveData(response.body());
                        response?.let { mutableLiveData.value = it.body() as ClimateModel }
                    }

                    override fun onFailure(call: Call<ClimateModel>, t: Throwable) {
                        Log.e("network_error",t.message.toString())
                    }
                })*/

       /* } catch (e: Exception) {
            Log.e("network_exception",e.message.toString())
        }*/
          //  return mutableLiveData
    }


    fun getClimateByLocations(lat:String,lon:String) = climateApi.getByLocations(lat, lon, Permissions.apiKey)
    fun getClimates(query: String) = climateApi.gets(query, Permissions.apiKey)
}