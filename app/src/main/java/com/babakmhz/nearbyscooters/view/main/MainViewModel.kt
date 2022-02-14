package com.babakmhz.nearbyscooters.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakmhz.nearbyscooters.appUtil.MainUiState
import com.babakmhz.nearbyscooters.appUtil.launchWithException
import com.babakmhz.nearbyscooters.data.RepositoryHelper
import com.babakmhz.nearbyscooters.data.domain.model.Scooter
import com.babakmhz.nearbyscooters.location.LocationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repositoryHelper: RepositoryHelper,
    private val locationHelper: LocationHelper
) : ViewModel() {

    val _scootersLiveData = MutableLiveData<MainUiState<List<Scooter>>>()
    val scooterLiveData: LiveData<MainUiState<List<Scooter>>> = _scootersLiveData

    init {
        fetchNearbyScooters()
    }

    fun fetchNearbyScooters() {
        viewModelScope.launchWithException(_scootersLiveData) {
            _scootersLiveData.postValue(MainUiState.Loading)
            val result = repositoryHelper.fetchScootersFromRemoteSource()
            _scootersLiveData.postValue(MainUiState.Success(result))
        }
    }


}