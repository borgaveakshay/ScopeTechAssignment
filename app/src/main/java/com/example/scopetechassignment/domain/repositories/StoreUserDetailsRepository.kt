package com.example.scopetechassignment.domain.repositories

import com.example.scopetechassignment.data.models.network.UserListResponse
import com.example.scopetechassignment.domain.Result

interface StoreUserDetailsRepository {

    suspend fun storeUserData(response: Result<UserListResponse>)
}