package com.yamasoft.vehiclecompanion.di

import com.yamasoft.vehiclecompanion.data.repository.PoiRepositoryImpl
import com.yamasoft.vehiclecompanion.data.repository.VehicleRepositoryImpl
import com.yamasoft.vehiclecompanion.domain.repository.PoiRepository
import com.yamasoft.vehiclecompanion.domain.repository.VehicleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPoiRepository(
        impl: PoiRepositoryImpl
    ): PoiRepository

    @Binds
    abstract fun bindVehicleRepository(
        impl: VehicleRepositoryImpl
    ): VehicleRepository
}