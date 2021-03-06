package com.example.mas_final.viewLayers.views.book

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.mas_final.R
import com.example.mas_final.data.dto.House
import com.example.mas_final.data.entity.Error
import com.example.mas_final.data.entity.Ok
import com.example.mas_final.extentions.uiScope
import com.example.mas_final.helpers.StringProvider
import com.example.mas_final.helpers.UTCHelper
import com.example.mas_final.repositories.ReservationRepository
import com.example.mas_final.viewLayers.views.base.BaseViewModel
import com.example.mas_final.viewLayers.views.book.components.BookReservationsAdapter
import com.google.gson.Gson
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BookViewModel(
    application: Application,
    strings: StringProvider,
    private val reservationRepo: ReservationRepository
): BaseViewModel(application, strings), LifecycleObserver{
    val activityEvent = MutableSharedFlow<ActivityEvents>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val reservationsAdapter = MutableStateFlow<BookReservationsAdapter?>(null)
    val noAvailableVisibility = MutableStateFlow(false)

    val dateFrom = MutableStateFlow(0L)
    val dateTo = MutableStateFlow(0L)

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        activityEvent.tryEmit(ActivityEvents.ShowDateRangePicker(dateFrom.value, dateTo.value))
    }

    fun onDateRangeSelected() {
        val utcFrom = UTCHelper().toUTC(dateFrom.value)
        val utcTo = UTCHelper().toUTC(dateTo.value)

        uiScope.launch {
            when (val res = reservationRepo.getReservationObjects(utcFrom, utcTo)){
                is Ok -> {
                    val reservations = arrayListOf<BookReservationsAdapter.Reservation>()

                    reservations.addAll((res.body.houses + res.body.rooms)
                        .map {
                            BookReservationsAdapter.Reservation(
                                it.name,
                                it.price,
                                if (it is House)
                                    R.drawable.house_image
                                else
                                    R.drawable.room_image,
                                it.id
                            )
                        })

                    reservationsAdapter.value = BookReservationsAdapter(reservations, strings)

                    noAvailableVisibility.value = reservations.isEmpty()
                }
                is Error -> {
                    showCommonErrors(res.error)
                }
            }
        }
    }

    fun onCalendarClick() {
        activityEvent.tryEmit(ActivityEvents.ShowDateRangePicker(dateFrom.value, dateTo.value))
    }

    fun onOkClick() {
        val selected = reservationsAdapter.value?.selectedReservations
        if (selected != null && selected.isNotEmpty()) {
            activityEvent.tryEmit(ActivityEvents.ShowConfirmationActivity(Gson().toJson(selected), dateFrom.value, dateTo.value))
        } else {
            error.tryEmit(strings.get(R.string.please_select_objects))
        }
    }

    sealed class ActivityEvents{
        class ShowDateRangePicker(val from: Long, val to: Long): ActivityEvents()
        class ShowConfirmationActivity(val reservations: String, val dateFrom: Long, val dateTo: Long): ActivityEvents()
    }
}