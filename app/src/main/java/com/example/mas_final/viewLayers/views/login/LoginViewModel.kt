package com.example.mas_final.viewLayers.views.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.mas_final.R
import com.example.mas_final.data.dto.LoginInfoDTO
import com.example.mas_final.data.dto.VAError
import com.example.mas_final.data.entity.Error
import com.example.mas_final.data.entity.Ok
import com.example.mas_final.extentions.sha256
import com.example.mas_final.extentions.uiScope
import com.example.mas_final.helpers.Preferences
import com.example.mas_final.helpers.StringProvider
import com.example.mas_final.repositories.PersonRepository
import com.example.mas_final.viewLayers.views.base.BaseViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    application: Application,
    strings: StringProvider,
    private val prefs: Preferences,
    private val personRepo: PersonRepository
) : BaseViewModel(application, strings) {
    var email = MutableLiveData<String>()
    var pass = MutableLiveData<String>()

    val activityEvent = MutableSharedFlow<ActivityEvents>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    fun onLoginClick() {
        if (validateNotEmpty() && validateEmail()
        ) {
            login()
        }
    }

    fun onCreateAccClick() {
        activityEvent.tryEmit(ActivityEvents.ShowRegisterActivity)
    }

    private fun validateEmail(): Boolean {
        if (!email.value!!.matches(Regex("[a-zA-Z0-9._-]+@([a-z]+\\.)+[a-z]+"))) {
            error.tryEmit(strings.get(R.string.bad_email))
            return false
        }

        return true
    }

    private fun validateNotEmpty(): Boolean {
        if (email.value.isNullOrEmpty()) {
            error.tryEmit(strings.get(R.string.all_fields_is_required))
            return false
        }
        val someEmpty = email.value.isNullOrEmpty() ||
                pass.value.isNullOrEmpty()

        if (someEmpty) {
            error.tryEmit(strings.get(R.string.all_fields_is_required))
            return false
        }

        return true
    }

    private fun login() {
        uiScope.launch {
            when (val res = personRepo.login(buildLoginInfo())) {
                is Ok -> {
                    prefs.client = res.body
                    prefs.token = res.body.token
                    activityEvent.tryEmit(ActivityEvents.CloseActivity)
                }
                is Error -> {
                    println(res)
                    when (res.error) {
                        VAError.BadLogin -> error.tryEmit(strings.get(R.string.bad_login))
                        else -> showCommonError(res.error)
                    }
                }
            }
        }
    }

    private fun buildLoginInfo() = LoginInfoDTO(email.value ?: "", sha256(pass.value ?: ""))

    sealed class ActivityEvents {
        object ShowRegisterActivity : ActivityEvents()
        object CloseActivity : ActivityEvents()
    }
}