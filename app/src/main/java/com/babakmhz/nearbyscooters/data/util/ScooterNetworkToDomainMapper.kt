package com.babakmhz.nearbyscooters.data.util

import com.babakmhz.nearbyscooters.data.domain.model.Scooter
import com.babakmhz.nearbyscooters.data.network.model.Current
import com.babakmhz.nearbyscooters.data.network.model.ScootersNetworkResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScooterNetworkToDomainMapper @Inject constructor() : DomainMapper<Current, Scooter> {


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
            domainModel.resolution?:"",
            domainModel.resolvedAt?:"",
            domainModel.resolvedBy?:"",
            domainModel.state,
            domainModel.vehicleId,
            domainModel.zoneId
        )
    }

    fun mapNetworkModelToScooterList(networkResponse:ScootersNetworkResponse):List<Scooter>{
        val current = networkResponse.data.current
        return current.map {
            mapToDomainModel(it)
        }
    }
}