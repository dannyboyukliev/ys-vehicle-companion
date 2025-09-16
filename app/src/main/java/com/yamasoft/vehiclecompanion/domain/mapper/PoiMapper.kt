package com.yamasoft.vehiclecompanion.domain.mapper

import com.yamasoft.vehiclecompanion.data.remote.dto.PoiDto
import com.yamasoft.vehiclecompanion.data.local.entity.PoiEntity
import com.yamasoft.vehiclecompanion.domain.model.Poi
import kotlin.math.roundToInt

// DTO to Domain (from API)
fun PoiDto.toDomain(): Poi {
    return Poi(
        id = id,
        name = name,
        url = url,
        category = primaryCategoryDisplayName,
        rating = rating?.roundToInt(),
        imageUrl = imageUrl,
        latitude = location?.getOrNull(0),
        longitude = location?.getOrNull(1),
        isFavorite = false
    )
}

// Entity to Domain (from Room)
fun PoiEntity.toDomain(): Poi {
    return Poi(
        id = id,
        name = name,
        url = url,
        category = category,
        rating = rating,
        imageUrl = imageUrl,
        latitude = latitude,
        longitude = longitude,
        isFavorite = true // All entities in DB are favorites
    )
}

// Domain to Entity (for saving favorites)
fun Poi.toEntity(): PoiEntity {
    return PoiEntity(
        id = id,
        name = name,
        url = url,
        category = category,
        rating = rating,
        imageUrl = imageUrl,
        latitude = latitude,
        longitude = longitude,
        savedAt = System.currentTimeMillis()
    )
}

// List extensions
fun List<PoiDto>.mapDtosToDomain(): List<Poi> = map { it.toDomain() }
fun List<PoiEntity>.mapEntitiesToDomain(): List<Poi> = map { it.toDomain() }