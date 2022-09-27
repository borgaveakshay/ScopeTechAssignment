package com.example.scopetechassignment.data.api

import com.example.scopetechassignment.data.models.network.UserListResponse
import com.example.scopetechassignment.data.models.network.VehicleLocationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserDetailsAPI {

    @GET("api/?op=list")
    suspend fun getUserDetails(): UserListResponse

    @GET("api/?op=getlocations")
    suspend fun getVehicleLocation(@Query("userid") userId: String): VehicleLocationResponse
}