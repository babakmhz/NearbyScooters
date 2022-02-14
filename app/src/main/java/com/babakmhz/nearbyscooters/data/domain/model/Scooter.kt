package com.babakmhz.nearbyscooters.data.domain.model

import com.google.android.gms.maps.model.LatLng

data class Scooter(
    val battery: Int,
    val fleetbirdId: Int,
    val hardwareId: String,
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val model: String,
    val resolution: String?,
    val resolvedAt: String?,
    val resolvedBy: String?,
    val state: String,
    val vehicleId: String,
    val zoneId: String
) {
    val latLng: LatLng get() = LatLng(latitude, longitude)

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Scooter) return false

        val otherLatLng = other.latLng

        if (latLng == otherLatLng)
            return true

        return false
    }

    override fun hashCode(): Int {
        return latLng.hashCode()
    }
}
