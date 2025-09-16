package com.yamasoft.vehiclecompanion.ui.screen.garage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yamasoft.vehiclecompanion.domain.model.Vehicle
import com.yamasoft.vehiclecompanion.domain.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GarageViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GarageUiState())
    val uiState: StateFlow<GarageUiState> = _uiState.asStateFlow()

    init {
        loadVehicles()
    }

    private fun loadVehicles() {
        viewModelScope.launch {
            vehicleRepository.getAllVehicles()
                .collect { vehicles ->
                    _uiState.update {
                        it.copy(
                            vehicles = vehicles,
                        )
                    }
                }
        }
    }

    fun deleteVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            vehicleRepository.deleteVehicle(vehicle)
        }
    }
}