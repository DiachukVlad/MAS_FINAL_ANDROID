package com.example.mas_final.viewLayers.views.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mas_final.R
import com.example.mas_final.data.entity.Error
import com.example.mas_final.data.entity.Ok
import com.example.mas_final.extentions.ioScope
import com.example.mas_final.extentions.uiScope
import com.example.mas_final.helpers.Preferences
import com.example.mas_final.helpers.StringProvider
import com.example.mas_final.repositories.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class RegisterViewModel(
    application: Application, private val strings: StringProvider,
    private val prefs: Preferences,
    val personRepo: PersonRepository
) : AndroidViewModel(application) {
    var state = MutableLiveData<VisibilityState>().apply { value = VisibilityState.First }
    var buttonText = MutableLiveData<String>().apply { value = strings.get(R.string.next) }

    var error: MutableSharedFlow<String> = MutableSharedFlow()

    var email = MutableLiveData<String>()
    var pass = MutableLiveData<String>()
    var passSecond = MutableLiveData<String>()
    var name = MutableLiveData<String>()
    var surname = MutableLiveData<String>()
    var birthday = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var extraPhone = MutableLiveData<String>()
    var location = MutableLiveData<String>()

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


    fun onButtonClick() {
        uiScope.launch {
            if (state.value == VisibilityState.First) {
                if (validateEmail()) {

                }
            } else {
                //TODO register
            }
        }
    }

    private suspend fun validateEmail(): Boolean {
        if (email.value.isNullOrEmpty()) {
            error.emit(strings.get(R.string.enter_email))
            return false
        }

        if (!email.value!!.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {

        }

        when (val res = personRepo.checkEmail("3")) {
            is Ok -> if (res.body) {
                state.value = VisibilityState.Second
                buttonText.value = strings.get(R.string.ok)
            }

            is Error -> {

            }
        }
        return true
    }
}