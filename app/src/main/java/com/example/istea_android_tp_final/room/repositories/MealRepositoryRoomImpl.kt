package com.example.istea_android_tp_final.room.repositories

import com.example.istea_android_tp_final.entities.meal.Meal
import com.example.istea_android_tp_final.repositories.MealRepository
import com.example.istea_android_tp_final.room.dao.MealDAO

class MealRepositoryRoomImpl(
    private val mealDAO: MealDAO
) : MealRepository {
    override suspend fun insert(meal: Meal): Boolean {
        return mealDAO.insert(meal) > 0
    }

    override suspend fun update(meal: Meal): Boolean {
        mealDAO.update(meal)
        return true
    }

    override suspend fun delete(meal: Meal): Boolean {
        mealDAO.delete(meal)
        return true
    }

}