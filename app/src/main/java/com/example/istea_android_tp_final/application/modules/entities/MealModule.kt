package com.example.istea_android_tp_final.application.modules.entities

import com.example.istea_android_tp_final.repositories.MealRepository
import com.example.istea_android_tp_final.room.AppDatabase
import com.example.istea_android_tp_final.room.repositories.MealRepositoryRoomImpl
import org.koin.dsl.module

val mealModule = module {
    single<MealRepository>(override = true) {
        MealRepositoryRoomImpl(
            get()
        )
    }
    single { get<AppDatabase>().mealDao() }
}