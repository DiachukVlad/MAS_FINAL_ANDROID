package com.example.mas_final.viewLayers.views.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mas_final.databinding.ActivityMainBinding
import com.example.mas_final.extentions.launchWhenCreated
import com.example.mas_final.extentions.launchWhenStarted
import com.example.mas_final.viewLayers.views.LoginViewModel
import com.example.mas_final.viewLayers.views.base.BaseActivity
import com.example.mas_final.viewLayers.views.login.LoginActivity
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding

    private val vm: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.exitButton.setOnClickListener { vm.onExitClick() }

        showText()
        observeEvents()
    }

    override fun onResume() {
        super.onResume()
        vm.onCreate()
    }

    private fun observeEvents() {
        vm.activityEvent.onEach {
            when (it) {
                MainViewModel.ActivityEvents.ShowLoginActivity -> {
                    startActivity(
                        Intent(
                            this,
                            LoginActivity::class.java
                        )
                    )
                }
            }
        }.launchWhenStarted(lifecycleScope)
    }

    private fun showText() {
        vm.mainText.onEach {
            binding.test.text = it
        }.launchWhenCreated(lifecycleScope)
    }
}