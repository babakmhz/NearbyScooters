package com.babakmhz.nearbyscooters.data

import com.babakmhz.nearbyscooters.data.domain.model.Scooter
import com.babakmhz.nearbyscooters.data.mapper.ScooterNetworkToDomainMapper
import com.babakmhz.nearbyscooters.data.network.ApiService
import com.babakmhz.nearbyscooters.location.LocationHelper
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val scooterMapper: ScooterNetworkToDomainMapper
) : RepositoryHelper {

    override suspend fun fetchScootersFromRemoteSource(
        userLatLng: LatLng
    ): List<Scooter> {
        return scooterMapper.mapNetworkModelToScooterList(
            apiService.getScooters(), userLatLng
        )
    }


}