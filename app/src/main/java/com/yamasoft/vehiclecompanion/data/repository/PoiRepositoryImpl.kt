package com.yamasoft.vehiclecompanion.data.repository

import com.yamasoft.vehiclecompanion.data.local.dao.PoiDao
import com.yamasoft.vehiclecompanion.data.remote.api.RoadtrippersApiService
import com.yamasoft.vehiclecompanion.domain.mapper.mapDtosToDomain
import com.yamasoft.vehiclecompanion.domain.mapper.mapEntitiesToDomain
import com.yamasoft.vehiclecompanion.domain.mapper.toEntity
import com.yamasoft.vehiclecompanion.domain.model.Poi
import com.yamasoft.vehiclecompanion.domain.repository.PoiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PoiRepositoryImpl @Inject constructor(
    private val apiService: RoadtrippersApiService,
    private val poiDao: PoiDao
): PoiRepository {
    override suspend fun discoverPois(
        swCorner: String,
        neCorner: String,
        pageSize: Int
    ): Result<List<Poi>> {
        return try {
            val response = apiService.discoverPois(swCorner, neCorner, pageSize)
            if (response.isSuccessful) {
                val pois = response.body()?.pois?.mapDtosToDomain() ?: emptyList()
                Result.success(pois)
            } else {
                Result.failure(Exception("API Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getFavoritePois(): Flow<List<Poi>> {
        return poiDao.getFavoritePois().map { entities ->
            entities.mapEntitiesToDomain()
        }
    }

    override suspend fun isPoiFavorite(id: Int): Boolean {
        return poiDao.isPoiFavorite(id)
    }

    override suspend fun toggleFavorite(poi: Poi): Boolean {
        return if (poiDao.isPoiFavorite(poi.id)) {
            poiDao.deletePoi(poi.id)
            false
        } else {
            poiDao.insertPoi(poi.toEntity())
            true
        }
    }

    override suspend fun addToFavorites(poi: Poi) {
        poiDao.insertPoi(poi.toEntity())
    }

    override suspend fun removeFromFavorites(poiId: Int) {
        poiDao.deletePoi(poiId)
    }
}