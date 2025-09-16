@file:OptIn(ExperimentalMaterial3Api::class)

package com.yamasoft.vehiclecompanion.ui.screen.garage.edit

import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.yamasoft.vehiclecompanion.ui.components.VehicleFormField

@Composable
fun EditVehicleScreen(
    navController: NavController,
    viewModel: EditVehicleViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    BackHandler {
        navController.navigateUp()
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { errorMessage ->
            snackbarHostState.showSnackbar(errorMessage)
        }
    }

    EditVehicleScreenContent(
        uiState = uiState,
        onNameChange = viewModel::updateName,
        onMakeChange = viewModel::updateMake,
        onModelChange = viewModel::updateModel,
        onYearChange = viewModel::updateYear,
        onBackButtonClick = {
            navController.navigateUp()
        },
        onUpdateButtonClick = viewModel::updateVehicle
    )
}

@Composable
fun EditVehicleScreenContent(
    uiState: EditVehicleUiState,
    onNameChange: (String) -> Unit,
    onMakeChange: (String) -> Unit,
    onModelChange: (String) -> Unit,
    onYearChange: (String) -> Unit,
    onBackButtonClick: () -> Unit,
    onUpdateButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Vehicle") },
                navigationIcon = {
                    IconButton(
                        onClick = onBackButtonClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
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
                    value = uiState.name,
                    onValueChange = onNameChange,
                    label = "Vehicle Name",
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Make and Model Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    VehicleFormField(
                        value = uiState.make,
                        onValueChange = onMakeChange,
                        label = "Make",
                        modifier = Modifier.weight(1f),
                    )

                    VehicleFormField(
                        value = uiState.model,
                        onValueChange = onModelChange,
                        label = "Model",
                        modifier = Modifier.weight(1f),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Year
                VehicleFormField(
                    value = uiState.year,
                    onValueChange = onYearChange,
                    label = "Year",
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Save Vehicle button
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onUpdateButtonClick,
                        modifier = Modifier.weight(1f),
                    ) {
                        Text("Update Vehicle")
                    }
                }
            }
        }
    }
}