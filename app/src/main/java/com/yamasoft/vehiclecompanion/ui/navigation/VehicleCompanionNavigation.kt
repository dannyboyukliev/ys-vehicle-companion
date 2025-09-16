package com.yamasoft.vehiclecompanion.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yamasoft.vehiclecompanion.ui.components.VehicleCompanionBottomBar
import com.yamasoft.vehiclecompanion.ui.screen.garage.add.AddVehicleScreen
import com.yamasoft.vehiclecompanion.ui.screen.garage.GarageScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    VehicleCompanionNavigation(navController = navController)
}

@Composable
fun VehicleCompanionNavigation(
    navController: NavHostController
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Determine if bottom bar should be shown (hide on detail screens)
    val showBottomBar = when (currentRoute) {
        Route.GARAGE, Route.PLACES, -> true
        else -> false
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                VehicleCompanionBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            // Pop up to start destination to avoid building up a large stack
                            popUpTo(Route.GARAGE) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Route.GARAGE,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Route.GARAGE) {
                GarageScreen(navController)
            }

            composable(Route.PLACES) {
                PlaceholderScreen(title = "Places", description = "POI discovery coming soon...")
            }

            composable(Route.ADD_VEHICLE) {
                AddVehicleScreen(navController)
            }
        }
    }
}

@Composable
private fun PlaceholderScreen(
    title: String,
    description: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$title\n\n$description",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
    }
}