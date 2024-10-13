package com.example.tablas.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tablas.Model.Vehicle

@Dao
interface VehicleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicle: Vehicle)

    @Query("SELECT * FROM vehicles")
    suspend fun getAllVehicles():List<Vehicle>

    @Query("DELETE FROM users WHERE id = :vehicleId")
    suspend fun deleteById(vehicleId: Int): Int

    @Query("""
        UPDATE vehicles
        SET 
            marca = COALESCE(:marca, marca), 
            modelo = COALESCE(:modelo, modelo), 
            caballos = COALESCE(:caballos, caballos)
        WHERE id = :vehicleId
    """)
    suspend fun updateVehicle(vehicleId: Int, marca: String?, modelo: String?, caballos: Int?): Int
}