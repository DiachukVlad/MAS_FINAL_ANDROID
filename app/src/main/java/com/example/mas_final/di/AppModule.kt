package com.example.mas_final.di

import com.example.mas_final.viewLayers.views.login.LoginViewModel
import com.example.mas_final.viewLayers.views.book.BookViewModel
import com.example.mas_final.viewLayers.views.main.MainViewModel
import com.example.mas_final.viewLayers.views.register.RegisterViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { RegisterViewModel(androidApplication(), get(), get(), get()) }
    viewModel { LoginViewModel(androidApplication(), get(), get(), get()) }
    viewModel { MainViewModel(androidApplication(), get(), get(), get()) }
    viewModel { BookViewModel(androidApplication(), get()) }
}