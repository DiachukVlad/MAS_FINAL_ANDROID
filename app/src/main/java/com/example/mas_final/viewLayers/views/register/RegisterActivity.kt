package com.example.mas_final.viewLayers.views.register

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.mas_final.R
import com.example.mas_final.databinding.ActivityRegisterBinding
import com.example.mas_final.extentions.launchWhenCreated
import com.example.mas_final.viewLayers.views.base.BaseActivity
import com.example.mas_final.viewLayers.views.main.MainActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.onEach
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity() {
    lateinit var binding: ActivityRegisterBinding

    private val vm: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        observerEvents()

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.button.setOnClickListener { vm.onButtonClick() }
        binding.birthday.setOnClickListener { vm.onBirthdayClick() }

        showState()
        showButtonText()
        showError(vm.error)
        showBirthday()
        showExtraPhone()
    }

    private fun showExtraPhone() {
        vm.showExtraPhone.onEach {
            binding.extraPhone.isVisible = it
        }.launchWhenCreated(lifecycleScope)
    }

    private fun observerEvents() {
        vm.activityEvent.onEach {
            when (it) {
                is RegisterViewModel.ActivityEvents.CloseActivity -> startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    ).apply { flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK }
                )
                is RegisterViewModel.ActivityEvents.ShowDatePicker -> showDatePicker(it.time)
            }
        }.launchWhenCreated(lifecycleScope)
    }

    private fun showBirthday() {
        vm.birthdayText.onEach {
            binding.birthday.text = it
        }.launchWhenCreated(lifecycleScope)
    }

    private fun showDatePicker(dateToShow: Long) {
        val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
        val datePicker: MaterialDatePicker<Long>

        if (dateToShow != 0L) {
            datePickerBuilder.setSelection(dateToShow)
            datePicker = datePickerBuilder.build()
            datePicker.show(supportFragmentManager, null)

            datePicker.addOnPositiveButtonClickListener {
                vm.onDateChange(it)
            }
        }
    }

    override fun connectToLiveData() = binding.run {
        arrayOf(
            email to vm.email,
            pass to vm.pass,
            passSecond to vm.passSecond,
            name to vm.name,
            surname to vm.surname,
            phone to vm.phone,
            extraPhone to vm.extraPhone,
            location to vm.location
        )
    }

    private fun showButtonText() {
        vm.buttonText.onEach {
            binding.button.text = it
        }.launchWhenCreated(lifecycleScope)
    }

    private fun showState() {
        vm.state.onEach {
            with(binding) {
                email.isVisible = it.email
                pass.isVisible = it.pass
                passSecond.isVisible = it.passSecond
                name.isVisible = it.nameText
                surname.isVisible = it.surname
                birthday.isVisible = it.birthday
                phone.isVisible = it.phone
                extraPhone.isVisible = it.extraPhone && vm.showExtraPhone.value
                location.isVisible = it.location
            }
        }.launchWhenCreated(lifecycleScope)
    }
}