package com.example.mas_final.helpers

import java.util.*

class UTCHelper {
    fun toUTC(time: Long) = time - delta
    fun toLocal(time: Long) = time + delta

    private val delta: Long
        get() {
            val local = System.currentTimeMillis()
            val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis
            return local - utc
        }
}