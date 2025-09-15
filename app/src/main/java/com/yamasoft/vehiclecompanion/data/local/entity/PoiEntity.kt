package com.yamasoft.vehiclecompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_pois")
data class PoiEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val url: String?,
    val category: String?,
    val rating: Int?,
    val imageUrl: String?,
    val latitude: Double?,
    val longitude: Double?,
    val savedAt: Long = System.currentTimeMillis()
)