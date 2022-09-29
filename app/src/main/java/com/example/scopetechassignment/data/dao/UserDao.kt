package com.example.scopetechassignment.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.scopetechassignment.data.models.db.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(entity: UserEntity)

    @Query("Select * from user_entity")
    fun retrieveAll(): List<UserEntity>

    @Query("Select * from user_entity")
    fun retrieveAllFromFlow(): Flow<List<UserEntity>>
}