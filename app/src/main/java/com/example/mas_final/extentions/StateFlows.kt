package com.example.mas_final.extentions

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect


fun <T> Flow<T>.launchWhenCreated(lifecycleScope: LifecycleCoroutineScope) {
    lifecycleScope.launchWhenCreated {
        this@launchWhenCreated.collect()
    }
}

fun <T> Flow<T>.launchWhenStarted(lifecycleScope: LifecycleCoroutineScope) {
    lifecycleScope.launchWhenCreated {
        this@launchWhenStarted.collect()
    }
}

fun <T> Flow<T>.launchWhenResumed(lifecycleScope: LifecycleCoroutineScope) {
    lifecycleScope.launchWhenCreated {
        this@launchWhenResumed.collect()
    }
}