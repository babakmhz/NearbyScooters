package com.babakmhz.nearbyscooters.view.main.map

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.babakmhz.nearbyscooters.R
import com.babakmhz.nearbyscooters.appUtil.LocationUiState
import com.babakmhz.nearbyscooters.databinding.FragmentMapsBinding
import com.babakmhz.nearbyscooters.view.base.BaseActivity
import com.babakmhz.nearbyscooters.view.base.BaseFragment
import com.babakmhz.nearbyscooters.view.main.MainViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : BaseFragment(R.layout.fragment_maps) {

    private lateinit var googleMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding

    private var userMarker: Marker? = null

    private val viewModel by lazy {
        getSharedViewModel(requireActivity() as BaseActivity,MainViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMapsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        viewModel.locationLiveData.observe(viewLifecycleOwner,locationLiveDataObserver)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private val locationLiveDataObserver = Observer<LocationUiState<LatLng>> {
        when(it){
            is LocationUiState.Loading->{
                // TODO: 2/14/22
            }
            is LocationUiState.Error -> {
                // TODO: 2/14/22
            }
            is LocationUiState.OnPermissionFailed -> {
                requestLocationPermission(requireActivity() as BaseActivity)
            }
            is LocationUiState.OnProviderDisabled -> showLocationProviderDisabledAlert(requireActivity())
            is LocationUiState.Success -> {
                // TODO: 2/14/22 add user's marker
            }
        }
    }


    override fun initializeUi() {
    }

    override fun getProgressBar(): ProgressBar? {
        TODO("Not yet implemented")
    }

}