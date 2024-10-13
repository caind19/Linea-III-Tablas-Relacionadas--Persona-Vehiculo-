// DAO/UserWithVehicle.kt
package com.example.tablas.DAO

import androidx.room.Embedded
import androidx.room.Relation
import com.example.tablas.Model.User
import com.example.tablas.Model.Vehicle

data class UserWithVehicle(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id", // Clave primaria de User
        entityColumn = "userId" // Clave foránea en Vehicle
    )
    val vehicle: Vehicle?
)


