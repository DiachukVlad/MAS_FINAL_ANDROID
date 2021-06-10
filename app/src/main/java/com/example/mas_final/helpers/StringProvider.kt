package com.example.mas_final.helpers

import com.example.mas_final.VAApplication
import org.koin.java.KoinJavaComponent

class StringProvider(private val application: VAApplication) {
    fun get(resId: Int): String {
        return application.getString(resId)
    }
}