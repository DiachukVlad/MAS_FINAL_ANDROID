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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class RegisterViewModel(
    application: Application, private val strings: StringProvider,
    private val prefs: Preferences,
    private val personRepo: PersonRepository
) : AndroidViewModel(application) {
    val state = MutableStateFlow(VisibilityState.First)
    val buttonText = MutableStateFlow(strings.get(R.string.next))
    var birthdayText = MutableStateFlow(strings.get(R.string.birthday))

    val showDatePicker = MutableSharedFlow<Long>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val error = MutableSharedFlow<String>(
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    val closeActivity = MutableSharedFlow<Boolean>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    var email = MutableLiveData<String>()
    var pass = MutableLiveData<String>()
    var passSecond = MutableLiveData<String>()
    var name = MutableLiveData<String>()
    var surname = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var extraPhone = MutableLiveData<String>()
    var location = MutableLiveData<String>()

    var birthday = System.currentTimeMillis()

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

    fun onBirthdayClick() {
        showDatePicker.tryEmit(birthday)
    }

    fun onDateChange(time: Long) {
        birthday = time
        val date: Date = Calendar.getInstance().apply { timeInMillis = time }.time
        val dateFormat: DateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        birthdayText.tryEmit(dateFormat.format(date))
    }

    private suspend fun register() {
        val person = buildPerson()
        when (val res = personRepo.register(person)) {
            is Ok -> {
                prefs.token = res.body
                prefs.person = person

                closeActivity.tryEmit(true)
            }
            is Error -> {
                showCommonError(res.error)
            }
        }
    }

    private fun buildPerson() = PersonDTO(
        name.value!!,
        surname.value!!,
        birthday,
        location.value!!,
        listOf(
            phone.value!!,
            extraPhone.value!!
        ),
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
                birthday == 0L ||
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

        return when (val res = personRepo.checkEmail(email.value ?: "")) {
            is Ok -> {
                if (res.body) { //if email exists
                    error.tryEmit(strings.get(R.string.email_exists))
                    false
                } else {
                    true
                }
            }

            is Error -> {
                showCommonError(res.error)
                false
            }
        }
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