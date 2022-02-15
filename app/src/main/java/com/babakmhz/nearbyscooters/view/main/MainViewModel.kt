package com.babakmhz.nearbyscooters.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakmhz.nearbyscooters.appUtil.LocationUiState
import com.babakmhz.nearbyscooters.appUtil.MainUiState
import com.babakmhz.nearbyscooters.appUtil.launchWithException
import com.babakmhz.nearbyscooters.data.RepositoryHelper
import com.babakmhz.nearbyscooters.data.domain.model.Scooter
import com.babakmhz.nearbyscooters.location.LocationHelper
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationToken
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repositoryHelper: RepositoryHelper,
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val _scootersLiveData = MutableLiveData<MainUiState<List<Scooter>>>()
    val scooterLiveData: LiveData<MainUiState<List<Scooter>>> = _scootersLiveData

    private val _locationLiveData = MutableLiveData<LocationUiState<LatLng>>()
    val locationLiveData: LiveData<LocationUiState<LatLng>> = _locationLiveData

    init {
        getLastLocation()
    }

    private fun onLocationPermissionFailed() =
        _locationLiveData.postValue(LocationUiState.OnPermissionFailed)


    private fun onLocationProviderDisabled() =
        _locationLiveData.postValue(LocationUiState.OnProviderDisabled)

    private fun onLocationLoading() = _locationLiveData.postValue(LocationUiState.Loading)

    private fun onLocationSuccess(latLng: LatLng) {
        _locationLiveData.postValue(LocationUiState.Success(latLng))
        // here we can pass the latLng to fetch scooters respect to user's location
        fetchScooters(latLng)
    }

    private fun fetchScooters(userLatLng: LatLng) {
        viewModelScope.launchWithException(_scootersLiveData) {
            _scootersLiveData.postValue(MainUiState.Loading)
            val result = repositoryHelper.fetchScootersFromRemoteSource(userLatLng,locationHelper)
            _scootersLiveData.postValue(MainUiState.Success(result))
        }
    }


    private fun onLocationFailed(exception: Exception) =
        _locationLiveData.postValue(LocationUiState.Error(exception))

    fun getLastLocation() {
        locationHelper.requestLastLocation(
            onPermissionFailed = this::onLocationPermissionFailed,
            onProviderDisabled = this::onLocationProviderDisabled,
            onLoading = this::onLocationLoading,
            onResult = {
                onLocationSuccess(it)
            },
            onFailure = {
                onLocationFailed(it)
            }
        )
    }

    fun requestLocationUpdates(cancellationToken: CancellationToken) {
        locationHelper.requestLocationUpdates(
            onPermissionFailed = this::onLocationPermissionFailed,
            onProviderDisabled = this::onLocationProviderDisabled,
            onLoading = this::onLocationLoading,
            onResult = {
                this.onLocationSuccess(it)
            },
            onFailure = {
                this.onLocationFailed(it)
            }, cancellationToken
        )
    }


}