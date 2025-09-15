package com.yamasoft.vehiclecompanion

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VehicleCompanionApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}