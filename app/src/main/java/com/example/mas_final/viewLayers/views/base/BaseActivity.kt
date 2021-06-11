package com.example.mas_final.viewLayers.views.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.mas_final.helpers.connectToLiveData
import com.example.mas_final.viewLayers.components.VAEditText

open class BaseActivity: AppCompatActivity() {
    override fun setContentView(view: View?) {
        super.setContentView(view)
        view?.post {
            connectToLiveData().forEach { (editText, liveData) ->
                editText.connectToLiveData(liveData)
            }
        }
    }

    open fun connectToLiveData(): Array<Pair<VAEditText, MutableLiveData<String>>> = arrayOf()
}