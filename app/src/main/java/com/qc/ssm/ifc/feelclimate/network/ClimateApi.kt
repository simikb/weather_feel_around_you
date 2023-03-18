package com.qc.ssm.ifc.feelclimate.network

import com.qc.ssm.ifc.feelclimate.models.ClimateModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ClimateApi {

    @GET("weather")
    suspend fun get(@Query("q") query: String, @Query("appid") appId: String): ClimateModel
}