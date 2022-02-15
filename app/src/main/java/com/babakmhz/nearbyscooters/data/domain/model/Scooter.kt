package com.babakmhz.nearbyscooters.data.domain.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import java.io.Serializable

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
    val zoneId: String,
) : Serializable, ClusterItem {

    val latLng: LatLng get() = LatLng(latitude, longitude)
    var itemTitle: String = ""
    var itemDescription: String = ""
    var distanceToUserLocation = 0

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Scooter) return false

        if (id == other.id)
            return true

        return false
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun getPosition(): LatLng = latLng

    override fun getTitle(): String = this.itemTitle

    override fun getSnippet(): String = this.itemDescription

    enum class ScooterState{
        ACTIVE,LOST,MAINTENANCE,LOW_BATTERY
    }

}
