package com.example.scopetechassignment.domain.usecases

import com.example.scopetechassignment.data.models.network.UserListResponse
import com.example.scopetechassignment.domain.Result
import com.example.scopetechassignment.domain.repositories.GetUserDetailsRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(private val repository: GetUserDetailsRepository) :
    BaseUseCase<Unit, Result<UserListResponse>>() {

    override suspend fun execute(request: Unit?): Result<UserListResponse> =
        repository.getUserList()
}