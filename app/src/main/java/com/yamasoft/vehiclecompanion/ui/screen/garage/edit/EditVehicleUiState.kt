package com.yamasoft.vehiclecompanion.ui.screen.garage.edit

data class EditVehicleUiState(
    val name: String = "",
    val make: String = "",
    val model: String = "",
    val year: String = "",
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
)