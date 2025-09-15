package com.yamasoft.vehiclecompanion.data.remote.api

import com.yamasoft.vehiclecompanion.data.remote.dto.PoiDiscoverResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RoadtrippersApiService {
    @GET("pois/discover")
    suspend fun discoverPois(
        @Query("sw_corner") swCorner: String,
        @Query("ne_corner") neCorner: String,
        @Query("page_size") pageSize: Int = 50
    ): Response<PoiDiscoverResponse>
}