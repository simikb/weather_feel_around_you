package com.qc.ssm.ifc.feelclimate.di

import com.qc.ssm.ifc.feelclimate.network.ClimateApi
import com.qc.ssm.ifc.feelclimate.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        climateApi: ClimateApi
    ): MainRepository {
        return MainRepository(climateApi)
    }
}