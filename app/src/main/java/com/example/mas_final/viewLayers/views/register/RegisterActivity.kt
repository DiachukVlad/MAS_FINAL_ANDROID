package com.example.mas_final.viewLayers.views.register

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.mas_final.R
import com.example.mas_final.databinding.ActivityRegisterBinding
import com.example.mas_final.viewLayers.views.base.BaseActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity() {
    lateinit var binding: ActivityRegisterBinding

    private val vm: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.button.setOnClickListener { vm.onButtonClick() }

        lifecycleScope.launchWhenCreated {
            showState()
            showButtonText()
            showError()
        }
    }

    private suspend fun showError() {
        vm.error.collect {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(getColor(R.color.red))
                .show()
        }
    }

    override fun connectToLiveData() = binding.run {
        arrayOf(
            email to vm.email,
            pass to vm.pass,
            passSecond to vm.passSecond,
            name to vm.name,
            surname to vm.surname,
            birthday to vm.birthday,
            phone to vm.phone,
            extraPhone to vm.extraPhone,
            location to vm.location
        )
    }

    private fun showButtonText() {
        vm.buttonText.observeForever {
            binding.button.text = it
        }
    }

    private fun showState() {
        vm.state.observeForever {
            with(binding) {
                email.isVisible = it.email
                pass.isVisible = it.pass
                passSecond.isVisible = it.passSecond
                name.isVisible = it.nameText
                surname.isVisible = it.surname
                birthday.isVisible = it.birthday
                phone.isVisible = it.phone
                extraPhone.isVisible = it.extraPhone
                location.isVisible = it.location
            }
        }
    }
}