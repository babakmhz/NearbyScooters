package com.babakmhz.nearbyscooters.data

import com.babakmhz.nearbyscooters.data.domain.model.Scooter
import com.babakmhz.nearbyscooters.data.network.ApiService
import com.babakmhz.nearbyscooters.data.util.ScooterNetworkToDomainMapper
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val scooterMapper:ScooterNetworkToDomainMapper
) : RepositoryHelper {

    override suspend fun fetchScootersFromRemoteSource(): List<Scooter> {
        return scooterMapper.mapNetworkModelToScooterList(
            apiService.getScooters()
        )
    }


}