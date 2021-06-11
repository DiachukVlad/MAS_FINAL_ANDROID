package com.example.mas_final.di

import android.content.Context
import android.content.SharedPreferences
import com.example.mas_final.VAApplication
import com.example.mas_final.data.dao.PersonDao
import com.example.mas_final.helpers.Preferences
import com.example.mas_final.helpers.StringProvider
import com.example.mas_final.repositories.PersonRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val dataModule = module {
    single { androidApplication() as VAApplication }
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "MainPrefs",
            Context.MODE_PRIVATE
        )
    }

    single { StringProvider(get()) }
    factory { Preferences(get()) }

    single {
        Retrofit.Builder()
            .baseUrl("http://192.168.0.101:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * DAOs
     */
    factory { get<Retrofit>().create(PersonDao::class.java) }

    /**
     * Repos
     */
    factory { PersonRepository(get()) }
}