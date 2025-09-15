package com.yamasoft.vehiclecompanion.di

import android.content.Context
import androidx.room.Room
import com.yamasoft.vehiclecompanion.data.local.VehicleCompanionDatabase
import com.yamasoft.vehiclecompanion.data.local.dao.PoiDao
import com.yamasoft.vehiclecompanion.data.local.dao.VehicleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideVehicleCompanionDatabase(
        @ApplicationContext context: Context
    ): VehicleCompanionDatabase {
        return Room.databaseBuilder(
            context,
            VehicleCompanionDatabase::class.java,
            "vehicle_companion_database"
        ).build()
    }

    @Provides
    fun provideVehicleDao(database: VehicleCompanionDatabase): VehicleDao {
        return database.vehicleDao()
    }

    @Provides
    fun providePoiDao(database: VehicleCompanionDatabase): PoiDao {
        return database.poiDao()
    }
}