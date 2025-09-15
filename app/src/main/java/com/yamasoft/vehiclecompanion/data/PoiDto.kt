package com.yamasoft.vehiclecompanion.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PoiDto(
    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "url")
    val url: String?,

    @Json(name = "primary_category_display_name")
    val primaryCategoryDisplayName: String?,

    @Json(name = "rating")
    val rating: Int?,

    @Json(name = "v_320x320_url")
    val imageUrl: String?,

    @Json(name = "loc")
    val location: List<Double>?
)