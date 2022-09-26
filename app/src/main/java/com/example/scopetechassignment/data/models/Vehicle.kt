package com.example.scopetechassignment.data.models


import com.google.gson.annotations.SerializedName

data class Vehicle(
    @SerializedName("color")
    val color: String,
    @SerializedName("foto")
    val photo: String,
    @SerializedName("make")
    val make: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("vehicleid")
    val vehicleId: Int,
    @SerializedName("vin")
    val vin: String,
    @SerializedName("year")
    val year: String
)