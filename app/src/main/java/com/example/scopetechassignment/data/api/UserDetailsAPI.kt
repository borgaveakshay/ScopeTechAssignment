package com.example.scopetechassignment.data.api

import com.example.scopetechassignment.data.models.UserListResponse
import retrofit2.http.GET

interface UserDetailsAPI {

    @GET("api/op=list")
    suspend fun getUserDetails(): UserListResponse
}