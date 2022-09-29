package com.example.scopetechassignment.data.models.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.scopetechassignment.data.dao.UserDao
import com.example.scopetechassignment.data.dao.VehicleInfoDao

@Database(entities = [UserEntity::class, VehicleInformationEntity::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getVehicleInfoDao(): VehicleInfoDao
}