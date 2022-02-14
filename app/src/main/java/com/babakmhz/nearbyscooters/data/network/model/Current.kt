package com.babakmhz.nearbyscooters.data.network.model

data class Current(
    val battery: Int,
    val fleetbirdId: Int,
    val hardwareId: String,
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val model: String,
    val resolution: String,
    val resolvedAt: String,
    val resolvedBy: String,
    val state: String,
    val vehicleId: String,
    val zoneId: String
)