package com.example.istea_android_tp_final.application.modules

import androidx.room.Room.databaseBuilder
import com.example.istea_android_tp_final.room.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        databaseBuilder(androidApplication(), AppDatabase::class.java, "saludable-db").fallbackToDestructiveMigration().build()
    }
}