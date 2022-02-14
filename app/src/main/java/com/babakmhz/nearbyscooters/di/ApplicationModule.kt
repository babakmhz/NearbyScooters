package com.babakmhz.nearbyscooters.di

import com.babakmhz.nearbyscooters.BuildConfig
import com.babakmhz.nearbyscooters.data.RepositoryHelper
import com.babakmhz.nearbyscooters.data.RepositoryImpl
import com.babakmhz.nearbyscooters.data.network.ApiService
import com.babakmhz.nearbyscooters.data.network.auth.AuthenticationInterceptor
import com.babakmhz.nearbyscooters.data.util.ScooterNetworkToDomainMapper
import com.babakmhz.nearbyscooters.location.LocationHelper
import com.babakmhz.nearbyscooters.location.LocationProvider
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authenticationInterceptor: AuthenticationInterceptor
    ) = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authenticationInterceptor)
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideLocationHelper(locationProvider: LocationProvider): LocationHelper = locationProvider

    @Provides
    @Singleton
    fun provideRepositoryHelper(repositoryImpl: RepositoryImpl): RepositoryHelper = repositoryImpl


}