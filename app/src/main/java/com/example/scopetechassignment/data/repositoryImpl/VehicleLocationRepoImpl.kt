package com.example.scopetechassignment.data.repositoryImpl

import com.example.scopetechassignment.data.api.UserDetailsAPI
import com.example.scopetechassignment.data.models.VehicleLocationResponse
import com.example.scopetechassignment.domain.Result
import com.example.scopetechassignment.domain.repositories.GetVehicleLocationRepository
import javax.inject.Inject

class VehicleLocationRepoImpl @Inject constructor(private val userDetailsAPI: UserDetailsAPI) :
    GetVehicleLocationRepository {

    override suspend fun getVehicleLocation(userId: String): Result<VehicleLocationResponse> = try {
        Result.success(userDetailsAPI.getVehicleLocation(userId))
    } catch (e: Exception) {
        Result.error(e)
    }
}