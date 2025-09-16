package com.yamasoft.vehiclecompanion.ui.screen.garage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.yamasoft.vehiclecompanion.R
import com.yamasoft.vehiclecompanion.domain.model.Vehicle
import com.yamasoft.vehiclecompanion.ui.components.VehicleCard
import com.yamasoft.vehiclecompanion.ui.navigation.Route
import com.yamasoft.vehiclecompanion.ui.theme.VehicleCompanionTheme

@Composable
fun GarageScreen(
    navController: NavController,
    viewModel: GarageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    GarageScreenContent(
        uiState = uiState,
        onAddVehicleClick = {
            navController.navigate(Route.ADD_VEHICLE)
        },
        onVehicleClick = { vehicle ->
            navController.navigate(Route.editVehicle(vehicleId = vehicle.id))
        },
        onDeleteVehicle = viewModel::deleteVehicle
    )
}

@Composable
private fun GarageScreenContent(
    uiState: GarageUiState,
    onAddVehicleClick: () -> Unit,
    onVehicleClick: (Vehicle) -> Unit,
    onDeleteVehicle: (Vehicle) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            uiState.vehicles.isEmpty() -> {
                EmptyGarageState(onAddVehicleClick = onAddVehicleClick)
            }

            // When there are vehicles
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        count = uiState.vehicles.size,
                    ) { index ->
                        val vehicle = uiState.vehicles[index]
                        VehicleCard(
                            vehicle = vehicle,
                            onVehicleClick = {
                                onVehicleClick(vehicle)
                            },
                            onDeleteButtonClick = {
                                onDeleteVehicle(vehicle)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyGarageState(
    onAddVehicleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .size(120.dp)
                .alpha(0.6f),
            painter = painterResource(R.drawable.car),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "No Vehicles Yet",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Add your first vehicle to start managing your garage and planning trips",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = onAddVehicleClick,
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Default.Add,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Add Your First Vehicle",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GarageScreenContentWithVehiclesPreview() {
    VehicleCompanionTheme {
        GarageScreenContent(
            uiState = GarageUiState(listOf(Vehicle(name = "My Car", make = "Toyota", model = "Avensis", year = 2009))),
            onAddVehicleClick = {},
            onDeleteVehicle = {},
            onVehicleClick = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun EmptyGarageStatePreview() {
    VehicleCompanionTheme {
        EmptyGarageState(
            onAddVehicleClick = {}
        )
    }
}