package com.example.scopetechassignment.data.models.network


import com.google.gson.annotations.SerializedName

data class UserListResponse(
    @SerializedName("data")
    val userData: List<Data>
)