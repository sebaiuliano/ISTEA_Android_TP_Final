package com.example.istea_android_tp_final.application

import androidx.multidex.MultiDexApplication
import com.example.istea_android_tp_final.application.modules.databaseModule
import com.example.istea_android_tp_final.application.modules.entities.mealModule
import com.example.istea_android_tp_final.application.modules.entities.userModule
import com.example.istea_android_tp_final.application.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class SaludableApp: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        startKoin{
            androidContext(this@SaludableApp)
            modules(listOf(
                databaseModule,
                viewModelModule,
                userModule,
                mealModule
            ))
        }
    }
}