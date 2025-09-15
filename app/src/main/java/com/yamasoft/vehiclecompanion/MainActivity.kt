package com.yamasoft.vehiclecompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yamasoft.vehiclecompanion.ui.navigation.MainScreen
import com.yamasoft.vehiclecompanion.ui.theme.VehicleCompanionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VehicleCompanionTheme {
                MainScreen()
            }
        }
    }
}