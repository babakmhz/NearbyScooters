package com.babakmhz.nearbyscooters.location

import android.location.Location
import com.google.android.gms.maps.model.LatLng

object LocationUtils {

    fun getDistanceBetween2Points(firstLatLng: LatLng, secondLatLng: LatLng): Int {
        val distance = FloatArray(1)
        Location.distanceBetween(
            firstLatLng.latitude, firstLatLng.longitude,
            secondLatLng.latitude, secondLatLng.longitude,
            distance
        )
        return distance[0].toInt()
    }

}