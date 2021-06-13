package com.example.mas_final.viewLayers.views.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.mas_final.R
import com.example.mas_final.extentions.launchWhenCreated
import com.example.mas_final.helpers.connectToLiveData
import com.example.mas_final.viewLayers.components.VAField
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onEach

open class BaseActivity: AppCompatActivity() {
    private var view: View? = null

    override fun setContentView(view: View?) {
        super.setContentView(view)
        view?.post {
            connectToLiveData().forEach { (editText, liveData) ->
                editText.connectToLiveData(liveData)
            }
        }
        this.view = view
    }

    protected fun showError(error: MutableSharedFlow<String>) {
        error.onEach {
            if (view != null) {
                Snackbar.make(view!!, it, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(getColor(R.color.red))
                    .show()
            }
        }.launchWhenCreated(lifecycleScope)
    }

    open fun connectToLiveData(): Array<Pair<VAField, MutableLiveData<String>>> = arrayOf()
}