package com.example.scopetechassignment.data.models


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("owner")
    val owner: Owner?,
    @SerializedName("userid")
    val userid: Int?,
    @SerializedName("vehicles")
    val vehicles: List<Vehicle>?
)