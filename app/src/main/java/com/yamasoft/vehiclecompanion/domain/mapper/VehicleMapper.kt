package com.yamasoft.vehiclecompanion.domain.mapper

import com.yamasoft.vehiclecompanion.data.local.entity.VehicleEntity
import com.yamasoft.vehiclecompanion.domain.model.Vehicle

// Entity to Domain
fun VehicleEntity.toDomain(): Vehicle {
    return Vehicle(
        id = id,
        name = name,
        make = make,
        model = model,
        year = year,
    )
}

// Domain to Entity
fun Vehicle.toEntity(): VehicleEntity {
    return VehicleEntity(
        id = if (id == 0L) 0L else id, // Room auto-generates if 0
        name = name,
        make = make,
        model = model,
        year = year,
        createdAt = System.currentTimeMillis()
    )
}

// List extensions
fun List<VehicleEntity>.toDomain(): List<Vehicle> = map { it.toDomain() }