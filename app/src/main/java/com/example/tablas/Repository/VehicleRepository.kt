package com.example.tablas.Repository

import com.example.tablas.DAO.VehicleDao
import com.example.tablas.DAO.VehicleWithUser
import com.example.tablas.Model.Vehicle

class VehicleRepository(private val vehicleDao: VehicleDao) {
    suspend fun insert(vehicle: Vehicle) {
        // Inserta el vehículo en la base de datos, asociándolo con el userId
        vehicleDao.insert(vehicle)
    }

    suspend fun getAllVehicles(): List<Vehicle> {
        return vehicleDao.getAllVehicles()
    }

    suspend fun deleteById(vehicleId: Int): Int {
        return vehicleDao.deleteById(vehicleId)
    }

    /*suspend fun delete(user: User) {
        userDao.delete(user)
    }*/

    suspend fun updateVehicle(vehicleId: Int, marca: String? =null , modelo: String? =null, caballos: Int? =null): Int {
        // Actualiza el vehículo y asegúrate de incluir userId en la lógica de actualización
        return vehicleDao.updateVehicle(vehicleId, marca, modelo, caballos)
    }

    suspend fun getVehicleWithUser(vehicleId: Int): VehicleWithUser {
        return vehicleDao.getVehicleWithUser(vehicleId)
    }
}
