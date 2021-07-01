package com.example.istea_android_tp_final.application.modules.entities

import com.example.istea_android_tp_final.repositories.UserRepository
import com.example.istea_android_tp_final.room.AppDatabase
import com.example.istea_android_tp_final.room.repositories.UserRepositoryRoomImpl
import org.koin.dsl.module

val userModule = module {
    single<UserRepository>(override = true) {
        UserRepositoryRoomImpl(
            get()
        )
    }
    single { get<AppDatabase>().userDao() }
}