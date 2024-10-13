package com.example.tablas.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey(autoGenerate=true)
    val id : Int =0,
    val marca: String,
    val modelo: String,
    val caballos: Int
)