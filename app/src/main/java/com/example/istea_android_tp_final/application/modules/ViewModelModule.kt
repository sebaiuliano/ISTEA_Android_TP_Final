package com.example.istea_android_tp_final.application.modules

import com.example.istea_android_tp_final.ui.login.LoginViewModel
import com.example.istea_android_tp_final.ui.meal.MealViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { MealViewModel(get()) }
}