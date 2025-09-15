package com.yamasoft.vehiclecompanion.data.repository

import com.yamasoft.vehiclecompanion.data.local.dao.VehicleDao
import com.yamasoft.vehiclecompanion.domain.mapper.toDomain
import com.yamasoft.vehiclecompanion.domain.mapper.toEntity
import com.yamasoft.vehiclecompanion.domain.model.Vehicle
import com.yamasoft.vehiclecompanion.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleRepositoryImpl @Inject constructor(
    private val vehicleDao: VehicleDao
): VehicleRepository {
    override fun getAllVehicles(): Flow<List<Vehicle>> {
        return vehicleDao.getAllVehicles().map { entities ->
            entities.toDomain()
        }
    }

    override suspend fun getVehicleById(id: Long): Vehicle? {
        return vehicleDao.getVehicleById(id)?.toDomain()
    }

    override suspend fun insertVehicle(vehicle: Vehicle): Long {
        return vehicleDao.insertVehicle(vehicle.toEntity())
    }

    override suspend fun updateVehicle(vehicle: Vehicle) {
        vehicleDao.updateVehicle(vehicle.toEntity())
    }

    override suspend fun deleteVehicle(vehicle: Vehicle) {
        vehicleDao.deleteVehicle(vehicle.toEntity())
    }

}