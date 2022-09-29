package com.example.scopetechassignment.domain.usecases

import com.example.scopetechassignment.domain.repositories.GetUserDetailsRepository
import com.example.scopetechassignment.domain.repositories.StoreUserDetailsRepository
import javax.inject.Inject

class StoreUserDetailsUseCase @Inject constructor(
    private val getUserDetailsRepository: GetUserDetailsRepository,
    private val storeUserDetailsRepository: StoreUserDetailsRepository
) : BaseUseCase<Unit, Unit>() {
    override suspend fun execute(request: Unit?) =
        storeUserDetailsRepository.storeUserData(getUserDetailsRepository.getUserList())
}