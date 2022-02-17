package com.babakmhz.nearbyscooters.data.mapper

import com.babakmhz.nearbyscooters.data.domain.model.Scooter
import com.babakmhz.nearbyscooters.data.network.model.Current
import com.babakmhz.nearbyscooters.data.network.model.ScootersNetworkResponse
import com.babakmhz.nearbyscooters.location.LocationHelper
import com.babakmhz.nearbyscooters.location.LocationUtils
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScooterNetworkToDomainMapper @Inject constructor() :
    DomainMapper<Current, Scooter> {


    override fun mapToDomainModel(model: Current): Scooter {
        return Scooter(
            model.battery,
            model.fleetbirdId,
            model.hardwareId,
            model.id,
            model.latitude,
            model.longitude,
            model.model,
            model.resolution,
            model.resolvedAt,
            model.resolvedBy,
            model.state,
            model.vehicleId,
            model.zoneId
        )
    }

    override fun mapFromDomainModel(domainModel: Scooter): Current {
        return Current(
            domainModel.battery,
            domainModel.fleetbirdId,
            domainModel.hardwareId,
            domainModel.id,
            domainModel.latitude,
            domainModel.longitude,
            domainModel.model,
            domainModel.resolution ?: "",
            domainModel.resolvedAt ?: "",
            domainModel.resolvedBy ?: "",
            domainModel.state,
            domainModel.vehicleId,
            domainModel.zoneId
        )
    }

    fun mapNetworkModelToScooterList(
        networkResponse: ScootersNetworkResponse,
        userLatLng: LatLng
    ): List<Scooter> {
        val current = networkResponse.data.current
        return current.asSequence().map {
            mapToDomainModel(it)
        }.map {
            it.apply {
                distanceToUserLocation =
                    LocationUtils.getDistanceBetween2Points(userLatLng, latLng)
            }
        }.toList()
    }
}