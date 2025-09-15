package com.yamasoft.vehiclecompanion.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PoiDiscoverResponse(
    @Json(name = "pois")
    val pois: List<PoiDto>
)