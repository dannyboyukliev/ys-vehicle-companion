package com.yamasoft.vehiclecompanion.data.service

import com.yamasoft.vehiclecompanion.data.remote.api.RoadtrippersApiService
import com.yamasoft.vehiclecompanion.data.remote.dto.PoiDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceService @Inject constructor(
    private val apiService: RoadtrippersApiService
) {
    suspend fun getPlaces(
        swCorner: String,
        neCorner: String,
        pageSize: Int = 50
    ): Result<List<PoiDto>> {
        return try {
            val response = apiService.discoverPois(swCorner, neCorner, pageSize)
            if (response.isSuccessful) {
                val places = response.body()?.pois ?: emptyList()
                Result.success(places)
            } else {
                Result.failure(Exception("API Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
