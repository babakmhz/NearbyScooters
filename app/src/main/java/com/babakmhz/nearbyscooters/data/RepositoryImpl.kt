package com.babakmhz.nearbyscooters.data

import com.babakmhz.nearbyscooters.data.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : RepositoryHelper {


}