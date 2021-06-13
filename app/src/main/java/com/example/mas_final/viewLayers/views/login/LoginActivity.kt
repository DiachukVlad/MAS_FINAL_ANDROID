package com.example.mas_final.viewLayers.views.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.mas_final.R
import com.example.mas_final.databinding.ActivityLoginBinding
import com.example.mas_final.extentions.launchWhenCreated
import com.example.mas_final.viewLayers.views.base.BaseActivity
import com.example.mas_final.viewLayers.views.register.RegisterActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity() {
    private val vm: LoginViewModel by inject()
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        showError(vm.error)

        binding.button.setOnClickListener { vm.onLoginClick() }
        binding.createButton.setOnClickListener { vm.onCreateAccClick() }

        vm.activityEvent.onEach {
            when (it) {
                is LoginViewModel.ActivityEvents.CloseActivity -> finish()
                is LoginViewModel.ActivityEvents.ShowRegisterActivity -> startActivity(
                    Intent(this, RegisterActivity::class.java)
                )
            }
        }.launchWhenCreated(lifecycleScope)
    }

    override fun connectToLiveData() = binding.run {
        arrayOf(
            email to vm.email,
            pass to vm.pass
        )
    }
}