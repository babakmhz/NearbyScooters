package com.babakmhz.nearbyscooters.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocationProvider @Inject constructor(@ApplicationContext private val context: Context) :
    LocationHelper {

    private val locationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }
    private lateinit var locationManager: LocationManager

    @SuppressLint("MissingPermission")
    override fun requestLastLocation(
        onPermissionFailed: () -> Unit,
        onProviderDisabled: () -> Unit,
        onLoading: () -> Unit,
        onResult: (LatLng) -> Unit,
        onFailure: (e: Exception) -> Unit
    ) {

        if (!checkUserCanBeLocated({ onProviderDisabled.invoke() },
                { onPermissionFailed.invoke() })
        )
            return

        onLoading.invoke()
        locationClient.lastLocation.addOnSuccessListener {
            if (it == null)
                onFailure.invoke(Exception("Last location is null"))
            else
                onResult.invoke(LatLng(it.latitude, it.longitude))
        }.addOnFailureListener {
            onFailure.invoke(it)
        }

    }

    @SuppressLint("MissingPermission")
    override fun requestLocationUpdates(
        onPermissionFailed: () -> Unit,
        onProviderDisabled: () -> Unit,
        onLoading: () -> Unit,
        onResult: (LatLng) -> Unit,
        onFailure: (e: Exception) -> Unit,
        cancellationToken: CancellationToken
    ) {
        if (!checkUserCanBeLocated(
                { onProviderDisabled.invoke() },
                { onPermissionFailed.invoke() })
        )
            return

        onLoading.invoke()
        locationClient.getCurrentLocation(
            LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
            cancellationToken
        )
            .addOnSuccessListener {
                onResult.invoke(LatLng(it.latitude, it.longitude))
            }.addOnFailureListener {
                onFailure.invoke(it)
            }
    }

    override fun isProviderDisabled(): Boolean {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return when {
            isGPSEnabled || isNetworkEnabled -> {
                false
            }
            else -> {
                true
            }
        }
    }

    override fun hasLocationPermission() = !(ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED)

    private fun checkUserCanBeLocated(
        onProviderDisabled: () -> Unit,
        onPermissionFailed: () -> Unit
    ): Boolean {
        if (!hasLocationPermission()) {
            onPermissionFailed.invoke()
            return false
        }

        if (isProviderDisabled()) {
            onProviderDisabled.invoke()
            return false
        }

        return true
    }

}