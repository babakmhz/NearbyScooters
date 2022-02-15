package com.babakmhz.nearbyscooters.location

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationToken


interface LocationHelper {

    fun requestLocationUpdates(
        onPermissionFailed: () -> Unit,
        onProviderDisabled: () -> Unit,
        onLoading: () -> Unit,
        onResult: (LatLng) -> Unit,
        onFailure: (e: Exception) -> Unit,
        cancellationToken: CancellationToken
    )

    fun requestLastLocation(
        onPermissionFailed: () -> Unit,
        onProviderDisabled: () -> Unit,
        onLoading: () -> Unit = {},
        onResult: (LatLng) -> Unit,
        onFailure: (e: Exception) -> Unit
    )

    fun isProviderDisabled(): Boolean

    fun hasLocationPermission(): Boolean

    fun getDistanceBetween2Points(firstLatLng: LatLng, secondLatLng: LatLng): Int


}