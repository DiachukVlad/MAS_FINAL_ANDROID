package com.example.mas_final.viewLayers.views.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mas_final.R
import com.example.mas_final.data.dto.VAError
import com.example.mas_final.helpers.StringProvider
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

open class BaseViewModel(application: Application, protected val strings: StringProvider): AndroidViewModel(application) {
    val error = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    protected fun showCommonError(vaError: VAError) {
        when (vaError) {
            VAError.ServerIsUnavailable -> error.tryEmit(strings.get(R.string.server_is_unavailable))
            else -> error.tryEmit(strings.get(R.string.unexpected_error))
        }
    }
}