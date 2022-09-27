package com.example.scopetechassignment.data.models.network


import com.google.gson.annotations.SerializedName

data class VehicleLocationModel(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("vehicleid")
    val vehicleId: Int
)