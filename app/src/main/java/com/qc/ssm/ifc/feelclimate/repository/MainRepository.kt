package com.qc.ssm.ifc.feelclimate.repository


import com.qc.ssm.ifc.feelclimate.models.ClimateModel
import com.qc.ssm.ifc.feelclimate.network.ClimateApi
import com.qc.ssm.ifc.feelclimate.utils.DataState
import com.qc.ssm.ifc.feelclimate.utils.Permissions
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository
@Inject
constructor(
    private val climateApi: ClimateApi,
) {
    suspend fun getClimate(query: String): Flow<DataState<ClimateModel>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkClimate = climateApi.get(query, Permissions.apiKey)
            /* val blogs = climateMapper.mapFromEntityList(networkBlogs)*/
            /*  for (blog in blogs) {
                  blogDao.insert(cacheMapper.mapToEntity(blog))
              }
              val cachedBlogs = blogDao.get()*/
            emit(DataState.Success(networkClimate))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}