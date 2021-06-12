package com.example.mas_final.viewLayers.views.main

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.mas_final.R
import com.example.mas_final.data.dto.House
import com.example.mas_final.data.dto.TokenDTO
import com.example.mas_final.data.entity.Error
import com.example.mas_final.data.entity.Ok
import com.example.mas_final.extentions.uiScope
import com.example.mas_final.helpers.Preferences
import com.example.mas_final.helpers.StringProvider
import com.example.mas_final.repositories.PersonRepository
import com.example.mas_final.viewLayers.views.base.BaseViewModel
import com.example.mas_final.viewLayers.views.main.components.ReservationsAdapter
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
    application: Application,
    strings: StringProvider,
    private val prefs: Preferences,
    private val personRepository: PersonRepository
) : BaseViewModel(application, strings), LifecycleObserver {
    val activityEvent = MutableSharedFlow<ActivityEvents>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val reservations = MutableStateFlow<List<ReservationsAdapter.Reservation>>(arrayListOf())

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        val token: TokenDTO? = prefs.token
        if (
            token == null ||
            token.uuid.isEmpty() ||
            token.expirationDate < Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis
        ) {
            activityEvent.tryEmit(ActivityEvents.ShowLoginActivity)
        } else {
            uiScope.launch {
                when (val res = personRepository.loginToken(token)) {
                    is Ok -> {
                        prefs.token = res.body.token
                        prefs.client = res.body

                        val reserves = arrayListOf<ReservationsAdapter.Reservation>()

                        res.body.reservations.forEach { r ->
                            reserves.addAll((r.houses + r.rooms)
                                .map {
                                    ReservationsAdapter.Reservation(
                                        it.name,
                                        r.dateFrom!!,
                                        r.dateTo!!,
                                        it.price,
                                        if (it is House)
                                            R.drawable.house_image
                                        else
                                            R.drawable.room_image
                                    )
                                })
                        }

                        reservations.value = reserves
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
        prefs.client = null
        activityEvent.tryEmit(ActivityEvents.ShowLoginActivity)
    }

    fun onBookClick() {
        activityEvent.tryEmit(ActivityEvents.ShowBookActivity)
    }

    sealed class ActivityEvents {
        object ShowLoginActivity : ActivityEvents()
        object ShowBookActivity : ActivityEvents()
    }
}