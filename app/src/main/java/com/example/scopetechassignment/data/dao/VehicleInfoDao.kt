package com.example.scopetechassignment.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.scopetechassignment.data.models.db.VehicleInformationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicleInformationEntity: VehicleInformationEntity)

    @Query("Select * from vehicle_information where user_id = :userId")
    fun retrieveAllBy(userId: Int): Flow<List<VehicleInformationEntity>>
}