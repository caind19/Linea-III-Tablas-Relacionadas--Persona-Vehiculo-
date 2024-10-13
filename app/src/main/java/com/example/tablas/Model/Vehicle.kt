package com.example.tablas.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "vehicles",
    foreignKeys = [
        ForeignKey(
            entity = User::class, // Referencia a la entidad User
            parentColumns = ["id"], // Clave primaria de User
            childColumns = ["userId"], // Esta columna se vincula con Vehicle
            onDelete = ForeignKey.CASCADE // Opcional: manejar la eliminación
        )
    ]
)
data class Vehicle(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val marca: String,
    val modelo: String,
    val caballos: Int
)