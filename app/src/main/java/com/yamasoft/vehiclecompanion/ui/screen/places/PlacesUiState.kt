package com.yamasoft.vehiclecompanion.ui.screen.places

import com.yamasoft.vehiclecompanion.domain.model.Poi

data class PlacesUiState(
    val isLoading: Boolean = false,
    val places: List<Poi> = emptyList(),
    val error: String? = null
)