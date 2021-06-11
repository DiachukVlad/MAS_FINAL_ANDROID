package com.example.mas_final.helpers

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MutableLiveData
import com.example.mas_final.viewLayers.components.VAEditText

fun EditText.connectToLiveData(data: MutableLiveData<String>) {
    this.doOnTextChanged { text, _, _, _ ->
        if (data.value.toString() != text.toString())
            data.value = text.toString()
    }
    data.observeForever {
        if (it.toString() != text.toString())
            setText(it)
    }
}

fun VAEditText.connectToLiveData(data: MutableLiveData<String>) {
    binding.editText.connectToLiveData(data)
}