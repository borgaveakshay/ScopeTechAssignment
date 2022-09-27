package com.example.scopetechassignment.data.models.network


import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("foto")
    val photo: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("surname")
    val surname: String
)