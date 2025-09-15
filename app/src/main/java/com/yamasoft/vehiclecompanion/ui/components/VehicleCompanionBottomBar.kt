package com.yamasoft.vehiclecompanion.ui.components

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.yamasoft.vehiclecompanion.ui.navigation.Route

@Composable
fun VehicleCompanionBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == Route.GARAGE,
            onClick = { onNavigate(Route.GARAGE) },
            icon = {},
            label = { Text("Garage") }
        )

        NavigationBarItem(
            selected = currentRoute == Route.PLACES,
            onClick = { onNavigate(Route.PLACES) },
            icon = {},
            label = { Text("Places") }
        )
    }
}