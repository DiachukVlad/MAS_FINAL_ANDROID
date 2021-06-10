package com.example.mas_final

import android.app.Application
import com.example.mas_final.di.appModule
import com.example.mas_final.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class VAApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@VAApplication)
            modules(appModule, dataModule)
        }
    }
}