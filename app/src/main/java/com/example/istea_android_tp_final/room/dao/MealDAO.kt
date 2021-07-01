package com.example.istea_android_tp_final.room.dao

import androidx.room.*
import com.example.istea_android_tp_final.entities.meal.Meal

@Dao
interface MealDAO {
    @Insert
    fun insert(meal: Meal):Long
    @Update
    fun update(meal: Meal)
    @Delete
    fun delete(meal: Meal)
}