package com.example.scopetechassignment.domain.usecases

import com.example.scopetechassignment.data.models.db.VehicleInformationEntity
import com.example.scopetechassignment.domain.Result
import com.example.scopetechassignment.domain.repositories.GetVehicleInformationRepository
import javax.inject.Inject

class GetVehicleDetailsFromDbUseCase @Inject constructor(private val vehicleInformationRepository: GetVehicleInformationRepository) :
    BaseUseCase<Int, Result<List<VehicleInformationEntity>>>() {
    override suspend fun execute(request: Int?): Result<List<VehicleInformationEntity>> =
        request?.let {
            vehicleInformationRepository.getVehicleInformation(it)
        } ?: Result.error("User Id cannot be null")

}