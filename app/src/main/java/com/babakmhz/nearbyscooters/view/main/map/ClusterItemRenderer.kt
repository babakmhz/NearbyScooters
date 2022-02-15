package com.babakmhz.nearbyscooters.view.main.map

import android.content.Context
import com.babakmhz.nearbyscooters.R
import com.babakmhz.nearbyscooters.data.domain.model.Scooter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class ClusterItemRenderer(
    private val context: Context,
    private val googleMap: GoogleMap,
    private val clusterManager: ClusterManager<Scooter>
) : DefaultClusterRenderer<Scooter>(context, googleMap, clusterManager) {

    override fun onBeforeClusterItemRendered(item: Scooter, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.electric_scooter))
    }

    override fun onBeforeClusterRendered(cluster: Cluster<Scooter>, markerOptions: MarkerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions)
    }

}