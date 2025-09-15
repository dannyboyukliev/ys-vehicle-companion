package com.yamasoft.vehiclecompanion.domain.repository

import com.yamasoft.vehiclecompanion.domain.model.Poi
import kotlinx.coroutines.flow.Flow

interface PoiRepository {
    suspend fun discoverPois(
        swCorner: String,
        neCorner: String,
        pageSize: Int = 50
    ): Result<List<Poi>>
    fun getFavoritePois(): Flow<List<Poi>>
    suspend fun isPoiFavorite(id: Int): Boolean
    suspend fun toggleFavorite(poi: Poi): Boolean
    suspend fun addToFavorites(poi: Poi)
    suspend fun removeFromFavorites(poiId: Int)
}