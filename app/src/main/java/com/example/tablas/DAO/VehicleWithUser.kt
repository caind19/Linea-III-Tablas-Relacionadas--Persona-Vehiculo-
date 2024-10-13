// DAO/VehicleWithUser.kt
package com.example.tablas.DAO

import androidx.room.Embedded
import androidx.room.Relation
import com.example.tablas.Model.User
import com.example.tablas.Model.Vehicle

data class VehicleWithUser(
    @Embedded val vehicle: Vehicle,
    @Relation(
        parentColumn = "userId", // Clave foránea en Vehicle
        entityColumn = "id" // Clave primaria de User
    )
    val user: User
)


