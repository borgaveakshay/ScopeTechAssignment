package com.example.scopetechassignment.data.repositoryImpl

import com.example.scopetechassignment.data.api.UserDetailsAPI
import com.example.scopetechassignment.data.models.network.UserListResponse
import com.example.scopetechassignment.domain.Result
import com.example.scopetechassignment.domain.repositories.GetUserDetailsRepository
import javax.inject.Inject

class GetUserDetailsRepositoryImpl @Inject constructor(private val userDetailsAPI: UserDetailsAPI) :
    GetUserDetailsRepository {
    override suspend fun getUserList(): Result<UserListResponse> = try {
        Result.success(userDetailsAPI.getUserDetails())
    } catch (e: Exception) {
        Result.error(e)
    }
}