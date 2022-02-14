package com.babakmhz.nearbyscooters.view.main.map

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import com.babakmhz.nearbyscooters.R
import com.babakmhz.nearbyscooters.databinding.FragmentMapsBinding
import com.babakmhz.nearbyscooters.view.base.BaseActivity
import com.babakmhz.nearbyscooters.view.base.BaseFragment
import com.babakmhz.nearbyscooters.view.main.MainViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : BaseFragment(R.layout.fragment_maps) {

    private lateinit var googleMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding

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

        viewModel

    }

    override fun initializeUi() {
    }

    override fun getProgressBar(): ProgressBar? {
        TODO("Not yet implemented")
    }

}