package com.example.scopetechassignment.domain.repositories

import com.example.scopetechassignment.data.models.db.VehicleInformationEntity
import com.example.scopetechassignment.domain.Result

interface GetVehicleInformationRepository {

    suspend fun getVehicleInformation(userId: Int): Result<List<VehicleInformationEntity>>
}