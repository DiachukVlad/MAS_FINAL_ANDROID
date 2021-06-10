package com.example.mas_final.viewLayers.views.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mas_final.R
import com.example.mas_final.data.dto.TokenDTO
import com.example.mas_final.helpers.Preferences
import com.example.mas_final.helpers.StringProvider
import java.util.*

class RegisterViewModel(
    application: Application, private val strings: StringProvider,
    private val prefs: Preferences
) : AndroidViewModel(application) {
    var state = MutableLiveData<VisibilityState>().apply { value = VisibilityState.First }
    var buttonText = MutableLiveData<String>().apply { value = strings.get(R.string.next) }

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
        if (state.value == VisibilityState.First) {
            //TODO validate email
            state.value = VisibilityState.Second
            buttonText.value = strings.get(R.string.ok)
        } else {
            //TODO register
        }
    }
}