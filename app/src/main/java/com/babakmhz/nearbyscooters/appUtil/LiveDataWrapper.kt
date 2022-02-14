package com.babakmhz.nearbyscooters.appUtil

import com.google.android.gms.maps.model.LatLng

sealed class MainUiState<out T : Any> {
    object Loading : MainUiState<Nothing>()
    data class Error(val error: Throwable? = null) : MainUiState<Nothing>()
    data class Success<out T : Any>(val data: T) : MainUiState<T>()
}

sealed class LocationUiState<out T : Any> {
    object Loading : LocationUiState<Nothing>()
    object OnProviderDisabled : LocationUiState<Nothing>()
    object OnPermissionFailed: LocationUiState<Nothing>()
    data class Error(val error: Throwable? = null) : LocationUiState<Nothing>()
    data class Success(val data: LatLng) : LocationUiState<LatLng>()
}
