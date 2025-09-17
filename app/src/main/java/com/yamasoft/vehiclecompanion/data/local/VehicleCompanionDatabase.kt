package com.yamasoft.vehiclecompanion.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yamasoft.vehiclecompanion.data.local.dao.PoiDao
import com.yamasoft.vehiclecompanion.data.local.dao.VehicleDao
import com.yamasoft.vehiclecompanion.data.local.entity.PoiEntity
import com.yamasoft.vehiclecompanion.data.local.entity.VehicleEntity

@Database(
    entities = [VehicleEntity::class, PoiEntity::class],
    version = 1,
    exportSchema = false
)
abstract class VehicleCompanionDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
    abstract fun poiDao(): PoiDao
}