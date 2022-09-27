package com.example.scopetechassignment.domain.repositories

import com.example.scopetechassignment.data.models.VehicleLocationResponse
import com.example.scopetechassignment.domain.Result

interface GetVehicleLocationRepository {

    suspend fun getVehicleLocation(userId: String): Result<VehicleLocationResponse>
}