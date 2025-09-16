package com.yamasoft.vehiclecompanion.ui.screen.garage.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yamasoft.vehiclecompanion.domain.model.Vehicle
import com.yamasoft.vehiclecompanion.domain.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditVehicleViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val vehicleId: Long = checkNotNull(savedStateHandle.get<String>("vehicleId")).toLong()

    private val _uiState = MutableStateFlow(EditVehicleUiState())
    val uiState: StateFlow<EditVehicleUiState> = _uiState.asStateFlow()

    init {
        loadVehicle()
    }

    private fun loadVehicle() {
        viewModelScope.launch {
            try {
                val vehicle = vehicleRepository.getVehicleById(vehicleId)
                if (vehicle != null) {
                    _uiState.value = _uiState.value.copy(
                        name = vehicle.name,
                        make = vehicle.make,
                        model = vehicle.model,
                        year = vehicle.year.toString()
                    )
                } else {
                    // TODO: Vehicle not found
                }
            } catch (e: Exception) {
                // TODO: Handle exception
            }

        }
    }

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(
            name = name
        )
    }

    fun updateMake(make: String) {
        _uiState.value = _uiState.value.copy(
            make = make
        )
    }

    fun updateModel(model: String) {
        _uiState.value = _uiState.value.copy(
            model = model
        )
    }

    fun updateYear(year: String) {
        _uiState.value = _uiState.value.copy(
            year = year
        )
    }

    fun saveVehicle() {
        val currentState = _uiState.value

        viewModelScope.launch {
            val vehicle = Vehicle(
                name = currentState.name.trim(),
                make = currentState.make.trim(),
                model = currentState.model.trim(),
                year = currentState.year.toInt(),
            )

            vehicleRepository.insertVehicle(vehicle)

            _uiState.value = _uiState.value.copy(
                isSuccess = true
            )
        }
    }
}