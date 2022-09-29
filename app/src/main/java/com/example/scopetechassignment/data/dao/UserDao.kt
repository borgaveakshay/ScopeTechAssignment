package com.example.scopetechassignment.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.scopetechassignment.data.models.db.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    fun insert(entity: UserEntity)

    @Query("Select * from user_entity where user_id =:userId ")
    fun retrieveAllBy(userId: Int)
}