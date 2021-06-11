package com.example.mas_final.extentions

import com.google.common.hash.Hashing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.nio.charset.StandardCharsets

val ioScope = CoroutineScope(Dispatchers.IO)
val uiScope = CoroutineScope(Dispatchers.Main)

fun sha256(str: String) = Hashing.sha256().hashString(str, StandardCharsets.UTF_8).toString()