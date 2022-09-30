package com.example.scopetechassignment.data.repositoryImpl

import android.database.sqlite.SQLiteException
import com.example.scopetechassignment.data.dao.VehicleInfoDao
import com.example.scopetechassignment.data.models.db.VehicleInformationEntity
import com.example.scopetechassignment.domain.Result
import com.example.scopetechassignment.domain.repositories.GetVehicleInformationRepository
import javax.inject.Inject

class GetVehicleInformationRepoImpl @Inject constructor(private val vehicleInfoDao: VehicleInfoDao) :
    GetVehicleInformationRepository {
    override suspend fun getVehicleInformation(userId: Int): Result<List<VehicleInformationEntity>> = try {
        Result.success(vehicleInfoDao.retrieveAllBy(userId))
    } catch (e: SQLiteException) {
        Result.error(e)
    }
}