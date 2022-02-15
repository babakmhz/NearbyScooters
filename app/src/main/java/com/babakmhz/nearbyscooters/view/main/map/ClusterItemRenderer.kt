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
    context: Context,
    googleMap: GoogleMap,
    private val clusterManager: ClusterManager<Scooter>
) : DefaultClusterRenderer<Scooter>(context, googleMap, clusterManager) {

    override fun onBeforeClusterItemRendered(item: Scooter, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        if (nearestActiveScooter != null && item == nearestActiveScooter) {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.electric_scooter_near))
        } else if (item.state == Scooter.ScooterState.ACTIVE.name)
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.electric_scooter_regular))
        else
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.electric_scooter_bw))
    }


    override fun onBeforeClusterRendered(cluster: Cluster<Scooter>, markerOptions: MarkerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions)
    }

    val nearestActiveScooter: Scooter?
        get() {
            val data = clusterManager.algorithm.items
            return data.asSequence().filter { it.state == Scooter.ScooterState.ACTIVE.name }
                .minByOrNull { it.distanceToUserLocation }
        }
}