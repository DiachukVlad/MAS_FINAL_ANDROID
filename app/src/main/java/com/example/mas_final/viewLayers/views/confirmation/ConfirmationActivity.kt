package com.example.mas_final.viewLayers.views.confirmation

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.mas_final.databinding.ActivityConfirmationBinding
import com.example.mas_final.extentions.launchWhenCreated
import com.example.mas_final.viewLayers.views.base.BaseActivity
import com.example.mas_final.viewLayers.views.main.MainActivity
import com.example.mas_final.viewLayers.views.register.RegisterViewModel
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class ConfirmationActivity : BaseActivity() {

    private lateinit var binding: ActivityConfirmationBinding
    private val vm: ConfirmationViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        showDataText()
        showSuccess()
        showError(vm.error)
        observerEvents()

        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm.intent = intent

        binding.confirmButton.setOnClickListener { vm.onConfirm() }
    }

    private fun showSuccess() {
        vm.showSuccess.onEach { show ->
            if (show) {
                val anim = ValueAnimator.ofFloat(0f,1f).apply {
                    duration = 500
                    addUpdateListener {
                        binding.successScreen.alpha = it.animatedValue as Float
                    }
                    start()
                }
            }
        }.launchWhenCreated(lifecycleScope)
    }

    private fun observerEvents() {
        vm.activityEvent.onEach {
            if (it is ConfirmationViewModel.ActivityEvents.CloseActivity) {
                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    ).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            }
        }.launchWhenCreated(lifecycleScope)
    }

    private fun showDataText() {
        vm.dataText.onEach {
            binding.dataText.text = it
        }.launchWhenCreated(lifecycleScope)
    }

    companion object {
        const val RESERVATIONS_JSON = "reservationsJson"
        const val DATE_FROM = "dateFrom"
        const val DATE_TO = "dateTo"
    }
}