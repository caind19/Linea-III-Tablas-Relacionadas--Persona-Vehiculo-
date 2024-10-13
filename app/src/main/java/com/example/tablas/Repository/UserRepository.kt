package com.example.tablas.Repository

import com.example.tablas.DAO.UserDao
import com.example.tablas.DAO.UserWithVehicle
import com.example.tablas.Model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun insert(user : User){
        userDao.insert(user)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    suspend fun deleteById(userId: Int): Int {
        return userDao.deleteById(userId)
    }

    /*suspend fun delete(user: User) {
        userDao.delete(user)
    }*/

    suspend fun updateUser(userId: Int, nombre: String?, apellido: String?, edad: Int?): Int {
            return userDao.updateUser(userId, nombre, apellido, edad)
    }

    suspend fun getUserWithVehicle(userId: Int): UserWithVehicle {
        return userDao.getUserWithVehicle(userId)
    }
}