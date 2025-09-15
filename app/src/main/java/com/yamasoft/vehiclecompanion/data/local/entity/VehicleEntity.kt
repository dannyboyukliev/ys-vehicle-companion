package com.yamasoft.vehiclecompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val make: String,
    val model: String,
    val year: Int,
    val createdAt: Long = System.currentTimeMillis()
)