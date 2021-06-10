package com.example.mas_final.extentions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

val ioScope = CoroutineScope(Dispatchers.IO)
val uiScope = CoroutineScope(Dispatchers.Main)
