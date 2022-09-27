package com.example.scopetechassignment.domain.usecases

import com.example.scopetechassignment.data.models.VehicleLocationResponse
import com.example.scopetechassignment.domain.Result
import com.example.scopetechassignment.domain.repositories.GetVehicleLocationRepository
import javax.inject.Inject

class GetVehicleLocationUseCase @Inject constructor(private val vehicleLocationRepository: GetVehicleLocationRepository) :
    BaseUseCase<String, Result<VehicleLocationResponse>>() {

    override suspend fun execute(request: String?): Result<VehicleLocationResponse> = request?.let {
        vehicleLocationRepository.getVehicleLocation(it)
    } ?: Result.error("User Id cannot be null")

}