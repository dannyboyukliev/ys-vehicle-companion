package com.yamasoft.vehiclecompanion.domain.model

data class Poi(
    val id: Int,
    val name: String,
    val url: String?,
    val category: String?,
    val rating: Int?,
    val imageUrl: String?,
    val latitude: Double?,
    val longitude: Double?,
    val isFavorite: Boolean = false
)