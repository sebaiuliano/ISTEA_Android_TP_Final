package com.example.istea_android_tp_final.repositories

import com.example.istea_android_tp_final.entities.meal.Meal

interface MealRepository {
    suspend fun insert(meal : Meal) : Boolean
    suspend fun update(meal : Meal) : Boolean
    suspend fun delete(meal : Meal) : Boolean
}