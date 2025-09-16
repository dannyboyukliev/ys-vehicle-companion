package com.yamasoft.vehiclecompanion.ui.screen.garage.add

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
class AddVehicleViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddVehicleUiState())
    val uiState: StateFlow<AddVehicleUiState> = _uiState.asStateFlow()

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
        }
    }
}