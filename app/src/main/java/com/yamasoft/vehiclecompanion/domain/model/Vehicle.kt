package com.yamasoft.vehiclecompanion.domain.model

data class Vehicle(
    val id: Long = 0,
    val name: String,
    val make: String,
    val model: String,
    val year: Int,
    val heroImagePath: String? = null
)