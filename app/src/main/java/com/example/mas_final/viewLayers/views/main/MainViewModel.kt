package com.example.mas_final.viewLayers.views.main

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.mas_final.data.dto.TokenDTO
import com.example.mas_final.data.entity.Error
import com.example.mas_final.data.entity.Ok
import com.example.mas_final.extentions.defScope
import com.example.mas_final.extentions.ioScope
import com.example.mas_final.extentions.uiScope
import com.example.mas_final.helpers.Preferences
import com.example.mas_final.helpers.StringProvider
import com.example.mas_final.repositories.PersonRepository
import com.example.mas_final.viewLayers.views.base.BaseViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
    application: Application,
    strings: StringProvider,
    private val prefs: Preferences,
    private val personRepository: PersonRepository
): BaseViewModel(application, strings) {
    var mainText = MutableStateFlow("")

    val activityEvent = MutableSharedFlow<ActivityEvents>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun onCreate() {
        val token: TokenDTO? = prefs.token
        if (
            token == null ||
            token.uuid.isEmpty() ||
            token.expirationDate < Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis
        ) {
            defScope.launch {
                //FIXME remove delay
                delay(1)
                activityEvent.emit(ActivityEvents.ShowLoginActivity)
            }
        } else {
            uiScope.launch {
                when(val res = personRepository.loginToken(token)) {
                    is Ok -> {
                        prefs.token = res.body.token
                        prefs.person = res.body
                        mainText.value = res.body.toString()
                    }
                    is Error -> {
                        activityEvent.tryEmit(ActivityEvents.ShowLoginActivity)
                    }
                }
            }
        }
    }

    fun onExitClick() {
        prefs.token = null
        prefs.person = null
        activityEvent.tryEmit(ActivityEvents.ShowLoginActivity)
    }

    sealed class ActivityEvents() {
        object ShowLoginActivity: ActivityEvents()
    }
}