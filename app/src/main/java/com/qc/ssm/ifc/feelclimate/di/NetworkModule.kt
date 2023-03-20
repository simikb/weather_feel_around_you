package com.qc.ssm.ifc.feelclimate.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.qc.ssm.ifc.feelclimate.network.ClimateApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val baseUrl = "https://api.openweathermap.org/data/2.5/"

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clientApi)
           // .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

    }

    @Singleton
    @Provides
    fun provideClimateService(retrofit: Retrofit.Builder): ClimateApi {
        return retrofit.build().create(ClimateApi::class.java)
    }

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    private val clientApi: OkHttpClient = OkHttpClient.Builder().apply {
        this.addNetworkInterceptor(interceptor)
    }.build()
}