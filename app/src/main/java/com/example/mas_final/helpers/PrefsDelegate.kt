package com.example.mas_final.helpers

import android.content.SharedPreferences
import com.example.mas_final.data.dto.TokenDTO
import com.google.gson.Gson
import org.koin.java.KoinJavaComponent.get
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


class PrefsDelegate<T>(private val key: String, private val default: T, private val clazz: KClass<TokenDTO>? = null) {
    private val sharedPreferences: SharedPreferences = get(SharedPreferences::class.java)

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return when (default) {
            is Int -> sharedPreferences.getInt(key, default) as T
            is Boolean -> sharedPreferences.getBoolean(key, default) as T
            is Long -> sharedPreferences.getLong(key, default) as T
            is Float -> sharedPreferences.getFloat(key, default) as T
            is String -> (sharedPreferences.getString(key, default) ?: default) as T
            else -> {
                val str = sharedPreferences.getString(key, "")
                clazz?.let { Gson().fromJson(str, clazz.java) as T } ?: default
            }
        }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        when (default) {
            is Int -> sharedPreferences.edit().putInt(key, value as Int).apply()
            is Boolean -> sharedPreferences.edit().putBoolean(key, value as Boolean).apply()
            is Long -> sharedPreferences.edit().putLong(key, value as Long).apply()
            is Float -> sharedPreferences.edit().putFloat(key, value as Float).apply()
            is String -> sharedPreferences.edit().putString(key, value as String).apply()
            else -> sharedPreferences.edit().putString(key, Gson().toJson(value)).apply()
        }
    }
}