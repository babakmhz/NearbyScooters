package com.babakmhz.nearbyscooters.location

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocationProvider @Inject constructor(@ApplicationContext private val context: Context) :
    LocationHelper {


}