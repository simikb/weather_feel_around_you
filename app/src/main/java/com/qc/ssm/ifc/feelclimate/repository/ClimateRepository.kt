package com.qc.ssm.ifc.feelclimate.repository

import com.qc.ssm.ifc.feelclimate.models.ClimateModel
import com.qc.ssm.ifc.feelclimate.network.ClimateApi
import com.qc.ssm.ifc.feelclimate.utils.DataState
import com.qc.ssm.ifc.feelclimate.utils.Permissions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClimateRepository
@Inject
constructor(
    private val climateApi: ClimateApi,
) {
    suspend fun getClimate(query: String): Flow<DataState<ClimateModel>> = flow {
        emit(DataState.Loading)
        try {
            try {
                val networkBlogs = climateApi.get(query, Permissions.apiKey)
                emit(DataState.Success(networkBlogs))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }


    suspend fun getClimateByLocation(lat: String, lon: String): Flow<DataState<ClimateModel>> =
        flow {
            try {
                val networkBlogs = climateApi.getByLocation(lat, lon, Permissions.apiKey)
                emit(DataState.Success(networkBlogs))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }


    fun getClimateByLocations(lat: String, lon: String) =
        climateApi.getByLocations(lat, lon, Permissions.apiKey)

    fun getClimates(query: String) = climateApi.gets(query, Permissions.apiKey)
}