package com.example.scopetechassignment.domain.repositories

import com.example.scopetechassignment.data.models.network.UserListResponse
import com.example.scopetechassignment.domain.Result

interface GetUserDetailsRepository {
    suspend fun getUserList(): Result<UserListResponse>
}