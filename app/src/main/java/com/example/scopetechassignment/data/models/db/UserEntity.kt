package com.example.scopetechassignment.data.models.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

private const val TABLE_NAME = "user_entity"

private const val FIELD_USER_ID = "user_id"
private const val FIELD_FIRST_NAME = "first_name"
private const val FIELD_SURNAME = "surname"
private const val FIELD_PHOTO = "photo_link"


@Entity(tableName = TABLE_NAME)
data class UserEntity(
    @ColumnInfo(name = FIELD_USER_ID)
    @PrimaryKey(autoGenerate = false)
    val userId: Int?,
    @ColumnInfo(name = FIELD_FIRST_NAME)
    val firstName: String?,
    @ColumnInfo(name = FIELD_SURNAME)
    val surname: String?,
    @ColumnInfo(name = FIELD_PHOTO)
    val photoLink: String?

)