package com.example.tablas.Repository

import com.example.tablas.DAO.VehicleDao
import com.example.tablas.Model.Vehicle

class VehicleRepository(private val vehicleDao: VehicleDao) {
    suspend fun insert(vehicle : Vehicle){
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

    suspend fun updateVehicle(vehicleId: Int, marca: String?, modelo: String?, caballos: Int?): Int {
        return vehicleDao.updateVehicle(vehicleId, marca, modelo, caballos)
    }
}
