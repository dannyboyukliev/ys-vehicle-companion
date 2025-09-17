package com.yamasoft.vehiclecompanion.ui.screen.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yamasoft.vehiclecompanion.domain.model.Poi
import com.yamasoft.vehiclecompanion.domain.repository.PoiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val poiRepository: PoiRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlacesUiState())
    val uiState: StateFlow<PlacesUiState> = _uiState.asStateFlow()

    init {
        loadPlaces()
    }

    private fun loadPlaces() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = poiRepository.discoverPois(
                swCorner = "-122.5280,37.7049",
                neCorner = "-122.3480,37.8349"
            )

            result.fold(
                onSuccess = { places ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        places = places
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message
                    )
                }
            )
        }
    }

    fun selectPlace(place: Poi) {
        _uiState.value = _uiState.value.copy(selectedPlace = place)
    }

    fun clearSelectedPlace() {
        _uiState.value = _uiState.value.copy(selectedPlace = null)
    }

    fun toggleFavoritePlace(place: Poi) {
        viewModelScope.launch {
            val newFavoriteStatus = poiRepository.toggleFavorite(place)

            val updatedPlaces = _uiState.value.places.map { poi ->
                if (poi.id == place.id) {
                    poi.copy(isFavorite = newFavoriteStatus)
                } else {
                    poi
                }
            }

            _uiState.value = _uiState.value.copy(places = updatedPlaces)

            if (_uiState.value.selectedPlace?.id == place.id) {
                _uiState.value = _uiState.value.copy(
                    selectedPlace = place.copy(isFavorite = newFavoriteStatus)
                )
            }
        }
    }
}