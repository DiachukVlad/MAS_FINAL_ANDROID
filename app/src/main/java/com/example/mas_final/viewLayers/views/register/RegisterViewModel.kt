package com.example.mas_final.viewLayers.views.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mas_final.R
import com.example.mas_final.data.dto.PersonDTO
import com.example.mas_final.data.dto.VAError
import com.example.mas_final.data.entity.Error
import com.example.mas_final.data.entity.Ok
import com.example.mas_final.extentions.sha256
import com.example.mas_final.extentions.uiScope
import com.example.mas_final.helpers.Preferences
import com.example.mas_final.helpers.StringProvider
import com.example.mas_final.repositories.PersonRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    application: Application, private val strings: StringProvider,
    private val prefs: Preferences,
    private val personRepo: PersonRepository
) : AndroidViewModel(application) {
    var state = MutableStateFlow(VisibilityState.First)
    var buttonText = MutableStateFlow(strings.get(R.string.next))

    var error: MutableSharedFlow<String> =
        MutableSharedFlow(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    var closeActivity: MutableSharedFlow<String> =
        MutableSharedFlow(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    var email = MutableLiveData<String>()
    var pass = MutableLiveData<String>()
    var passSecond = MutableLiveData<String>()
    var name = MutableLiveData<String>()
    var surname = MutableLiveData<String>()
    var birthday = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var extraPhone = MutableLiveData<String>()
    var location = MutableLiveData<String>()

    fun onButtonClick() {
        uiScope.launch {
            if (state.value == VisibilityState.First) {
                if (
                    validateNotEmpty() &&
                    validateEmail() &&
                    validatePasswords()
                ) {
                    state.value = VisibilityState.Second
                    buttonText.value = strings.get(R.string.ok)
                }
            } else {
                if (validateNotEmpty()) {
                    register()
                }
            }
        }
    }

    private suspend fun register() {
        val person = buildPerson()
        when(val res = personRepo.register(person)) {
            is Ok -> {
                prefs.token = res.body
                prefs.person = person

                println(prefs.token)
                println(prefs.person)
            }
            is Error -> {
                showCommonError(res.error)
            }
        }
    }

    private fun buildPerson() = PersonDTO(
        name.value!!,
        surname.value!!,
        //TODO date
        0L,
        location.value!!,
        listOf(phone.value!!,
            extraPhone.value!!),
        email.value!!,
        sha256(pass.value!!),
    )

    private fun validatePasswords(): Boolean {
        val isEqual = pass.value == passSecond.value
        if (!isEqual) {
            error.tryEmit(strings.get(R.string.passwords_must_be_equal))
        }

        return isEqual
    }

    private fun validateNotEmpty(): Boolean {
        if (email.value.isNullOrEmpty()) {
            error.tryEmit(strings.get(R.string.all_fields_is_required))
            return false
        }
        val someEmptyFirst = email.value.isNullOrEmpty() ||
                pass.value.isNullOrEmpty() ||
                passSecond.value.isNullOrEmpty()

        val someEmptySecond = name.value.isNullOrEmpty() ||
                surname.value.isNullOrEmpty() ||
                birthday.value.isNullOrEmpty() ||
                phone.value.isNullOrEmpty() ||
                extraPhone.value.isNullOrEmpty() ||
                location.value.isNullOrEmpty()

        if (someEmptyFirst || (state.value == VisibilityState.Second && someEmptySecond)) {
            error.tryEmit(strings.get(R.string.all_fields_is_required))
            return false
        }

        return true
    }

    private suspend fun validateEmail(): Boolean {
        if (!email.value!!.matches(Regex("[a-zA-Z0-9._-]+@([a-z]+\\.)+[a-z]+"))) {
            error.tryEmit(strings.get(R.string.bad_email))
            return false
        }

        when (val res = personRepo.checkEmail(email.value ?: "")) {
            is Ok -> {
                return if (res.body) { //if email exists
                    error.tryEmit(strings.get(R.string.email_exists))
                    false
                } else {
                    true
                }
            }

            is Error -> showCommonError(res.error)
        }
        return true
    }

    private fun showCommonError(vaError: VAError) {
        when (vaError) {
            VAError.ServerIsUnavailable -> error.tryEmit(strings.get(R.string.server_is_unavailable))
            else -> error.tryEmit(strings.get(R.string.unexpected_error))
        }
    }


    enum class VisibilityState(
        val email: Boolean = false,
        val pass: Boolean = false,
        val passSecond: Boolean = false,
        val nameText: Boolean = false,
        val surname: Boolean = false,
        val birthday: Boolean = false,
        val phone: Boolean = false,
        val extraPhone: Boolean = false,
        val location: Boolean = false
    ) {
        First(true, true, true),
        Second(false, false, false, true, true, true, true, true, true)
    }
}