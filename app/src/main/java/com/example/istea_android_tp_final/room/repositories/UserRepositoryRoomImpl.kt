package com.example.istea_android_tp_final.room.repositories

import com.example.istea_android_tp_final.entities.user.User
import com.example.istea_android_tp_final.repositories.UserRepository
import com.example.istea_android_tp_final.room.dao.UserDAO

class UserRepositoryRoomImpl(
    private val userDAO: UserDAO
) : UserRepository {
    override suspend fun insert(user: User): Boolean {
        return userDAO.insert(user) > 0
    }

    override suspend fun update(user: User): Boolean {
        userDAO.update(user)
        return true
    }

    override suspend fun delete(user: User): Boolean {
        userDAO.delete(user)
        return true
    }

    override suspend fun getByUserName(username: String): User? {
        return userDAO.getByUserName(username)
    }
}