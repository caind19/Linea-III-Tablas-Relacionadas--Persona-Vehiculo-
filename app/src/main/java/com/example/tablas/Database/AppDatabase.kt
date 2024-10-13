package com.example.tablas.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tablas.DAO.UserDao
import com.example.tablas.DAO.VehicleDao
import com.example.tablas.Model.User
import com.example.tablas.Model.Vehicle

// Incluye tanto la entidad User como Vehicle en el parámetro "entities"
@Database(entities = [User::class, Vehicle::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Define los métodos abstractos para los DAO
    abstract fun userDao(): UserDao
    abstract fun vehicleDao(): VehicleDao

    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}