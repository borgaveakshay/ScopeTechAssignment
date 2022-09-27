package com.example.scopetechassignment.data.models.network


import com.google.gson.annotations.SerializedName

data class VehicleLocationResponse(
    @SerializedName("data")
    val vehicleGeoData: List<VehicleLocationModel>
)