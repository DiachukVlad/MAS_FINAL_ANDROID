package com.example.mas_final.viewLayers.views.confirmation

import android.app.Application
import android.content.Intent
import androidx.lifecycle.LifecycleObserver
import com.example.mas_final.R
import com.example.mas_final.data.entity.Error
import com.example.mas_final.data.entity.Ok
import com.example.mas_final.data.requests.ReserveRequest
import com.example.mas_final.extentions.uiScope
import com.example.mas_final.helpers.Preferences
import com.example.mas_final.helpers.StringProvider
import com.example.mas_final.helpers.UTCHelper
import com.example.mas_final.repositories.ReservationRepository
import com.example.mas_final.viewLayers.views.base.BaseViewModel
import com.example.mas_final.viewLayers.views.book.components.BookReservationsAdapter
import com.example.mas_final.viewLayers.views.register.RegisterViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ConfirmationViewModel(
    application: Application,
    strings: StringProvider,
    private val prefs: Preferences,
    val reservationRepo: ReservationRepository
) : BaseViewModel(application, strings), LifecycleObserver {
    var intent: Intent? = null
        set(value) {
            field = value
            value?.run {
                val reservations = Gson().fromJson<Array<BookReservationsAdapter.Reservation>>(
                    getStringExtra(ConfirmationActivity.RESERVATIONS_JSON),
                    TypeToken.getArray(BookReservationsAdapter.Reservation::class.java).type
                )
                val from = getLongExtra(ConfirmationActivity.DATE_FROM, 0L)
                val to = getLongExtra(ConfirmationActivity.DATE_TO, 0L)

                val dateFrom: Date = Calendar.getInstance().apply { timeInMillis = from }.time
                val dateTo: Date = Calendar.getInstance().apply { timeInMillis = to }.time
                val dateFormat: DateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())

                val client = prefs.client!!

                var reservationsString = "Reservation objects:"
                reservations.forEach {
                    reservationsString+="\n\t" + it.name
                }

                dataText.value = strings.get(R.string.confirm_format).format(
                    client.name.capitalize(Locale.getDefault()), client.surname.capitalize(Locale.getDefault()),
                    client.phones[0],
                    reservationsString,
                    dateFormat.format(dateFrom),
                    dateFormat.format(dateTo)
                )

                reserveRequest = ReserveRequest(
                    reservations.map { it.id },
                    UTCHelper().toUTC(from),
                    UTCHelper().toUTC(to),
                    prefs.token!!
                )
            }
        }

    var dataText = MutableStateFlow("")
    val activityEvent = MutableSharedFlow<ActivityEvents>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val showSuccess = MutableStateFlow(false)

    private var reserveRequest: ReserveRequest? = null

    fun onConfirm() {
        if (reserveRequest == null) {
            error.tryEmit(strings.get(R.string.unexpected_error))
            return
        }

        uiScope.launch {
            when (val res = reservationRepo.bookObjects(reserveRequest!!)) {
                is Ok -> {
                    showSuccess.value = true
                    delay(2000)
                    activityEvent.tryEmit(ActivityEvents.CloseActivity)
                }
                is Error -> {
                    showCommonErrors(res.error)
                }
            }
        }
    }

    sealed class ActivityEvents {
        object CloseActivity: ActivityEvents()
    }
}