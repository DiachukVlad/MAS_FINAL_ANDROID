package com.example.mas_final.viewLayers.views.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import androidx.core.view.isVisible
import com.example.mas_final.databinding.ActivityRegisterBinding
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    private val vm: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeState()
        observeButtonText()

        binding.button.setOnClickListener { vm.onButtonClick() }
    }

    private fun observeButtonText() {
        vm.buttonText.observeForever {
            binding.button.text = it
        }
    }

    private fun observeState() {
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