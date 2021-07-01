package com.example.istea_android_tp_final.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.istea_android_tp_final.entities.meal.Meal
import com.example.istea_android_tp_final.entities.user.User
import com.example.istea_android_tp_final.room.dao.MealDAO
import com.example.istea_android_tp_final.room.dao.UserDAO

@Database(entities = [
    User::class,
    Meal::class
    ],
    version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDAO
    abstract fun mealDao() : MealDAO
}