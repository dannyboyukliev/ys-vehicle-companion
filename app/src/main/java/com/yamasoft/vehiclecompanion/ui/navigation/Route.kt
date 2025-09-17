package com.yamasoft.vehiclecompanion.ui.navigation

object Route {
    const val GARAGE = "garage"
    const val PLACES = "places"

    // Detail Routes
    const val ADD_VEHICLE = "add_vehicle"
    const val EDIT_VEHICLE = "edit_vehicle/{vehicleId}"

    fun editVehicle(vehicleId: Long) = "edit_vehicle/$vehicleId"
}