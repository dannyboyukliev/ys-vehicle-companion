package com.yamasoft.vehiclecompanion.ui.screen.places.detail

import com.yamasoft.vehiclecompanion.domain.model.Poi

data class PlaceDetailUiState(
    val selectedPlace: Poi? = null,
    val isVisible: Boolean = false
)