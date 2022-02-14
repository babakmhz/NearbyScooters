package com.babakmhz.nearbyscooters.data

import com.babakmhz.nearbyscooters.data.domain.model.Scooter


interface RepositoryHelper {

    suspend fun fetchScootersFromRemoteSource():List<Scooter>

}