@file:OptIn(ExperimentalMaterial3Api::class)

package com.yamasoft.vehiclecompanion.ui.screen.garage.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yamasoft.vehiclecompanion.ui.components.VehicleFormField
import com.yamasoft.vehiclecompanion.ui.theme.VehicleCompanionTheme

@Composable
fun AddVehicleScreen(navController: NavController) {

    AddVehicleScreenContent()
}

@Composable
private fun AddVehicleScreenContent() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Vehicle") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Vehicle Name
                VehicleFormField(
                    value = "",
                    onValueChange = {},
                    label = "Vehicle Name",
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Make and Model Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    VehicleFormField(
                        value = "",
                        onValueChange = {},
                        label = "Make",
                        modifier = Modifier.weight(1f),
                    )

                    VehicleFormField(
                        value = "",
                        onValueChange = {},
                        label = "Model",
                        modifier = Modifier.weight(1f),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Year
                VehicleFormField(
                    value = "",
                    onValueChange = {},
                    label = "Year",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddVehicleScreenPreview() {
    VehicleCompanionTheme {
        VehicleFormField(
            value = "2",
            onValueChange = {},
            label = "Model"
        )
    }
}