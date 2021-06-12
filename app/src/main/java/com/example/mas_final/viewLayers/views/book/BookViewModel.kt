package com.example.mas_final.viewLayers.views.book

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.mas_final.data.entity.Error
import com.example.mas_final.data.entity.Ok
import com.example.mas_final.extentions.uiScope
import com.example.mas_final.helpers.StringProvider
import com.example.mas_final.helpers.UTCHelper
import com.example.mas_final.repositories.ReservationRepository
import com.example.mas_final.viewLayers.views.base.BaseViewModel
import com.example.mas_final.viewLayers.views.main.MainViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BookViewModel(
    application: Application,
    strings: StringProvider,
    val reservationRepo: ReservationRepository
): BaseViewModel(application, strings), LifecycleObserver{
    val activityEvent = MutableSharedFlow<ActivityEvents>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val dateFrom = MutableStateFlow(0L)
    val dateTo = MutableStateFlow(0L)

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        activityEvent.tryEmit(ActivityEvents.ShowDateRangePicker(dateFrom.value, dateTo.value))
    }

    fun onDateRangeSelected() {
        val utcFrom = UTCHelper().toUTC(dateFrom.value)
        val utcTo = UTCHelper().toUTC(dateTo.value)

        println("utcFrom = ${utcFrom}")
        println("utcTo = ${utcTo}")

        uiScope.launch {
            when (val res = reservationRepo.getReservationObjects(utcFrom, utcTo)){
                is Ok -> {
                    println("OK ${res.body}")
                }
                is Error -> {

                }
            }
        }
    }

    fun onCalendarClick() {
        activityEvent.tryEmit(ActivityEvents.ShowDateRangePicker(dateFrom.value, dateTo.value))
    }

    sealed class ActivityEvents{
        class ShowDateRangePicker(val from: Long, val to: Long): ActivityEvents()
    }
}