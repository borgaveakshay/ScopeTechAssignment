package com.example.scopetechassignment.data.repositoryImpl

import com.example.scopetechassignment.data.dao.UserDao
import com.example.scopetechassignment.data.dao.VehicleInfoDao
import com.example.scopetechassignment.data.models.db.UserEntity
import com.example.scopetechassignment.data.models.db.VehicleInformationEntity
import com.example.scopetechassignment.data.models.network.UserListResponse
import com.example.scopetechassignment.domain.Result
import com.example.scopetechassignment.domain.Status
import com.example.scopetechassignment.domain.repositories.StoreUserDetailsRepository
import javax.inject.Inject

class StoreUserDetailsRepoImpl @Inject constructor(
    private val userDao: UserDao,
    private val vehicleInfoDao: VehicleInfoDao
) :
    StoreUserDetailsRepository {

    override suspend fun storeUserData(response: Result<UserListResponse>) {
        when (response.status) {
            Status.SUCCESS -> {
                response.data?.let { userListResponse ->
                    userListResponse.userData.map { data ->
                        data.userid?.let {
                            data.owner?.let { owner ->
                                userDao.insert(
                                    UserEntity(
                                        userId = data.userid,
                                        firstName = owner.name,
                                        surname = owner.surname,
                                        photoLink = owner.photo
                                    )
                                )
                            }
                            data.vehicles?.map { vehicle ->
                                vehicleInfoDao.insert(
                                    VehicleInformationEntity(
                                        vehicleId = vehicle.vehicleId,
                                        color = vehicle.color,
                                        photo = vehicle.photo,
                                        make = vehicle.make,
                                        model = vehicle.model,
                                        vin = vehicle.vin,
                                        year = vehicle.year,
                                        userId = data.userid
                                    )
                                )
                            }
                        }
                    }
                }
            }
            else -> {
                // Do nothing.
            }
        }
    }
}