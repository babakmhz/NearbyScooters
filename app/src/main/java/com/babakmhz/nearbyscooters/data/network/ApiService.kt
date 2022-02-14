package com.babakmhz.nearbyscooters.data.network

import com.babakmhz.nearbyscooters.data.network.model.ScootersNetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{latLng}")
    suspend fun getScooters(
        @Path("latLng")
        latLng: String = EndPoints.PATH_TO_BERLIN_SCOOTERS
    ): ScootersNetworkResponse

}