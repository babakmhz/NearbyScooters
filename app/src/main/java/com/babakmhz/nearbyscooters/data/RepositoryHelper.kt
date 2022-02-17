package com.babakmhz.nearbyscooters.data

import com.babakmhz.nearbyscooters.data.domain.model.Scooter
import com.babakmhz.nearbyscooters.location.LocationHelper
import com.google.android.gms.maps.model.LatLng


interface RepositoryHelper {


    suspend fun fetchScootersFromRemoteSource(
        userLatLng: LatLng
    ): List<Scooter>
}