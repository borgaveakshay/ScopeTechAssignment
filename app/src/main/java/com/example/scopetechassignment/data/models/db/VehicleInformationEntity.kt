package com.example.scopetechassignment.data.models.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

private const val TABLE_NAME = "vehicle_information"

private const val FIELD_COLOR = "color"
private const val FIELD_PHOTO = "photo"
private const val FIELD_MAKE = "make"
private const val FIELD_MODEL = "model"
private const val FIELD_VEHICLE_ID = "vehicle_id"
private const val FIELD_VIN = "vin"
private const val FIELD_YEAR = "year"
private const val FIELD_USER_ID = "user_id"

@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = arrayOf(FIELD_USER_ID),
        childColumns = arrayOf(FIELD_USER_ID),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class VehicleInformationEntity(
    @ColumnInfo(name = FIELD_VEHICLE_ID)
    @PrimaryKey(autoGenerate = false)
    val vehicleId: Int,
    @ColumnInfo(name = FIELD_COLOR)
    val color: String,
    @ColumnInfo(name = FIELD_PHOTO)
    val photo: String,
    @ColumnInfo(name = FIELD_MAKE)
    val make: String,
    @ColumnInfo(name = FIELD_MODEL)
    val model: String,
    @ColumnInfo(name = FIELD_VIN)
    val vin: String,
    @ColumnInfo(name = FIELD_YEAR)
    val year: String,
    @ColumnInfo(name = FIELD_USER_ID)
    val userId: Int?
)
