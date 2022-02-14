package com.babakmhz.nearbyscooters.data.network.auth

import com.babakmhz.nearbyscooters.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationInterceptor @Inject constructor(
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val newRequest = request.addHeader("secret-key", BuildConfig.SECRET_KEY).build()
        return chain.proceed(newRequest)
    }
}