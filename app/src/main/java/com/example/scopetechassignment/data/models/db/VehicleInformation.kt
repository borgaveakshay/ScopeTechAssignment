package com.example.scopetechassignment.data.models.db

import com.google.gson.annotations.SerializedName

data class VehicleInformation(
    val color: String,
    val photo: String,
    val make: String,
    val model: String,
    val vehicleId: Int,
    val vin: String,
    val year: String
)
