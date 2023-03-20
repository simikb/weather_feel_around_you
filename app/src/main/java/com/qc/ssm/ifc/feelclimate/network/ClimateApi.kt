package com.qc.ssm.ifc.feelclimate.network

import com.qc.ssm.ifc.feelclimate.models.ClimateModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ClimateApi {

   //work
   @GET("weather")
     fun gets(@Query("q") query: String, @Query("appid") appId: String): Call<ClimateModel>
    @GET("weather")
    fun getByLocations(@Query("lat") lat: String,@Query("lon") lon: String, @Query("appid") appId: String): Call<ClimateModel>
    @GET("weather")
    suspend fun get(@Query("q") query: String, @Query("appid") appId: String): ClimateModel
    @GET("weather")
     fun getByLocation(@Query("lat") lat: String,@Query("lon") lon: String, @Query("appid") appId: String): ClimateModel

}