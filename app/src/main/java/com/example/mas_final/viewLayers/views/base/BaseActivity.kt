package com.example.mas_final.viewLayers.views.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.mas_final.helpers.connectToLiveData
import com.example.mas_final.viewLayers.components.VAField

open class BaseActivity: AppCompatActivity() {
    override fun setContentView(view: View?) {
        super.setContentView(view)
        view?.post {
            connectToLiveData().forEach { (editText, liveData) ->
                editText.connectToLiveData(liveData)
            }
        }
    }

    open fun connectToLiveData(): Array<Pair<VAField, MutableLiveData<String>>> = arrayOf()
}