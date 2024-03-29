package com.qc.ssm.ifc.feelclimate.di

import com.qc.ssm.ifc.feelclimate.network.ClimateApi
import com.qc.ssm.ifc.feelclimate.repository.ClimateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        climateApi: ClimateApi
    ): ClimateRepository {
        return ClimateRepository(climateApi)
    }
}
