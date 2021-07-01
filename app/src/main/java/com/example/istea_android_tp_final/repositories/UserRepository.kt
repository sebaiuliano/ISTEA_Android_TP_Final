package com.example.istea_android_tp_final.repositories

import com.example.istea_android_tp_final.entities.meal.Meal
import com.example.istea_android_tp_final.entities.user.User

interface UserRepository {
    suspend fun insert(user : User) : Boolean
    suspend fun update(user : User) : Boolean
    suspend fun delete(user : User) : Boolean
    suspend fun getByUserName(userName : String) : User?
}