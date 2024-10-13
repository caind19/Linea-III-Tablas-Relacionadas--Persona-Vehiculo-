package com.example.tablas.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tablas.Model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers():List<User>

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteById(userId: Int): Int

    @Query("""
        UPDATE users
        SET 
            nombre = COALESCE(:nombre, nombre), 
            apellido = COALESCE(:apellido, apellido), 
            edad = COALESCE(:edad, edad)
        WHERE id = :userId
    """)
    suspend fun updateUser(userId: Int, nombre: String?, apellido: String?, edad: Int?): Int
}