package com.yamasoft.vehiclecompanion.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.yamasoft.vehiclecompanion.R
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
            icon = {
                Icon(
                    painter = painterResource(R.drawable.directions_car),
                    contentDescription = null
                )
            },
            label = { Text("Garage") }
        )

        NavigationBarItem(
            selected = currentRoute == Route.PLACES,
            onClick = { onNavigate(Route.PLACES) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = null
                )
            },
            label = { Text("Places") }
        )
    }
}