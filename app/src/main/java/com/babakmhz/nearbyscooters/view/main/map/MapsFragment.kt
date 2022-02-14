package com.babakmhz.nearbyscooters.view.main.map

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.babakmhz.nearbyscooters.R
import com.babakmhz.nearbyscooters.appUtil.LOCATION_PERMISSION_REQUEST_CODE
import com.babakmhz.nearbyscooters.appUtil.LocationUiState
import com.babakmhz.nearbyscooters.appUtil.MainUiState
import com.babakmhz.nearbyscooters.data.domain.model.Scooter
import com.babakmhz.nearbyscooters.databinding.FragmentMapsBinding
import com.babakmhz.nearbyscooters.view.base.BaseActivity
import com.babakmhz.nearbyscooters.view.base.BaseFragment
import com.babakmhz.nearbyscooters.view.main.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : BaseFragment(R.layout.fragment_maps) {

    private lateinit var googleMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding

    private var scooters: HashMap<Scooter, Marker>? = hashMapOf()

    private val cancellationToken: CancellationToken by lazy {
        CancellationTokenSource().token
    }


    private var userMarker: Marker? = null

    private val viewModel by lazy {
        getSharedViewModel(requireActivity() as BaseActivity, MainViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        setOnMarkerClickListener()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMapsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        viewModel.locationLiveData.observe(viewLifecycleOwner, locationLiveDataObserver)
        viewModel.scooterLiveData.observe(viewLifecycleOwner, scootersObserver)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.getLastLocation()
        } else {
            showLocationPermissionMessage()
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private val locationLiveDataObserver = Observer<LocationUiState<LatLng>> {
        when (it) {
            is LocationUiState.Loading -> {
                // TODO: 2/14/22
            }
            is LocationUiState.Error -> {
                showSnackBar(requireView(), getString(R.string.error_user_location, it.error))
            }
            is LocationUiState.OnPermissionFailed -> {
                requestLocationPermission(this)
            }
            is LocationUiState.OnProviderDisabled ->
                showLocationProviderDisabledAlert(requireActivity())

            is LocationUiState.Success -> {
                putUserMarker(it.data)
            }
        }
    }

    override fun onDestroy() {
        this.scooters = null
        super.onDestroy()
    }

    private val scootersObserver = Observer<MainUiState<List<Scooter>>> {
        when (it) {
            is MainUiState.Error -> {
            }
            MainUiState.Loading -> {
            }
            is MainUiState.Success -> {
                it.data.forEach { scooter ->
                    addMarker(scooter, R.drawable.electric_scooter)
                }
            }
        }
    }

    fun setOnMarkerClickListener() {
        googleMap.setOnMarkerClickListener { marker ->
            if (marker != userMarker) {
                val scooter = scooters?.filterValues { it == marker }!!.keys.first()
                val action = MapsFragmentDirections.actionMapsFragmentToDetailsFragment(
                    scooter
                )
                findNavController().navigate(action)
            }
            true
        }
    }

    private fun addMarker(scooter: Scooter, icon: Int?, title: String = "") {
        val markerOptions = MarkerOptions().position(scooter.latLng).apply {
            title(title)
            icon?.let {
                icon(BitmapDescriptorFactory.fromResource(icon))
            }

        }
        if (!scooters!!.containsKey(scooter)) {
            val marker = googleMap.addMarker(markerOptions)
            scooters!![scooter] = marker!!
        }

    }


    private fun putUserMarker(latLng: LatLng) {
        userMarker?.remove()
        val markerOptions = MarkerOptions().position(latLng).apply {
            title(getString(R.string.you_are_here))
        }
        googleMap.addMarker(markerOptions)?.let {
            userMarker = it
        }

        moveCameraToPosition(latLng)
    }

    override fun initializeUi() {
        binding.fabLocateMe.setOnClickListener {
            viewModel.requestLocationUpdates(cancellationToken)
        }
    }

    override fun getProgressBar(): ProgressBar? {
        TODO("Not yet implemented")

    }

    private fun moveCameraToPosition(latLng: LatLng) {
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(latLng, 12F),
            600,
            null
        )
    }

    private fun showLocationPermissionMessage() =
        showSnackBar(requireView(), getString(R.string.allow_permission_for_using_the_app))
}