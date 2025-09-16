@file:OptIn(ExperimentalMaterial3Api::class)

package com.yamasoft.vehiclecompanion.ui.screen.places

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.yamasoft.vehiclecompanion.ui.components.PoiCard
import com.yamasoft.vehiclecompanion.ui.components.VehicleCard
import com.yamasoft.vehiclecompanion.ui.screen.places.detail.PlaceDetailBottomSheet
import com.yamasoft.vehiclecompanion.ui.theme.VehicleCompanionTheme

@Composable
fun PlacesScreen(
    navController: NavController,
    viewModel: PlacesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scaffoldState = rememberBottomSheetScaffoldState()

    // Handle bottom sheet visibility based on selected place
    LaunchedEffect(uiState.selectedPlace) {
        if (uiState.selectedPlace != null) {
            scaffoldState.bottomSheetState.partialExpand()
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            uiState.selectedPlace?.let { place ->
                val isExpanded = scaffoldState.bottomSheetState.targetValue == SheetValue.Expanded
                PlaceDetailBottomSheet(
                    place = place,
                    isExpanded = isExpanded,
                    onDismiss = { viewModel.clearSelectedPlace() },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        },
        sheetPeekHeight = if (uiState.selectedPlace != null) 200.dp else 0.dp
    ) {
        PlacesScreenContent(
            uiState = uiState,
            onPlaceClick = { place ->
                viewModel.selectPlace(place)
            }
        )
    }
}

@Composable
private fun PlacesScreenContent(
    uiState: PlacesUiState,
    onPlaceClick: (com.yamasoft.vehiclecompanion.domain.model.Poi) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            uiState.isLoading -> {
                LoadingPlacesState()
            }

            uiState.error != null -> {
                ErrorPlacesState(uiState.error) { }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    items(
                        count = uiState.places.size,
                    ) { index ->
                        val poi = uiState.places[index]
                        PoiCard(
                            poi = poi,
                            modifier = Modifier.clickable {
                                onPlaceClick(poi)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingPlacesState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Loading places...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ErrorPlacesState(
    errorMessage: String,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Error",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.height(48.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Failed to load places",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onRetryClick
            ) {
                Text("Retry")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingPlacesStatePreview() {
    VehicleCompanionTheme {
        LoadingPlacesState()
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorPlacesStatePreview() {
    VehicleCompanionTheme {
        ErrorPlacesState(
            errorMessage = "Network connection failed. Please check your internet connection.",
            onRetryClick = {}
        )
    }
}