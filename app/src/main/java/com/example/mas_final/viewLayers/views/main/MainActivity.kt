package com.example.mas_final.viewLayers.views.main

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.mas_final.databinding.ActivityMainBinding
import com.example.mas_final.extentions.launchWhenCreated
import com.example.mas_final.helpers.StringProvider
import com.example.mas_final.viewLayers.views.base.BaseActivity
import com.example.mas_final.viewLayers.views.book.BookActivity
import com.example.mas_final.viewLayers.views.login.LoginActivity
import com.example.mas_final.viewLayers.views.main.components.ReservationsAdapter
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding

    private val vm: MainViewModel by inject()
    private val strings: StringProvider by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        observeEvents()
        showError(vm.error)
        showNoReservations()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.exitButton.setOnClickListener { vm.onExitClick() }
        binding.bookButton.setOnClickListener { vm.onBookClick() }

        lifecycle.addObserver(vm)

        showReservations()
    }

    private fun showNoReservations() {
        vm.noReservationsVisibility.onEach {
            binding.noReservations.isVisible = it
        }.launchWhenCreated(lifecycleScope)
    }

    private fun showReservations() {
        vm.reservations.onEach {
            if (it.isEmpty()) {
                binding.recycler.adapter = null
                return@onEach
            }

            val adapter = ReservationsAdapter(vm.reservations.value, strings)
            binding.recycler.adapter = adapter

        }.launchWhenCreated(lifecycleScope)
    }

    private fun observeEvents() {
        vm.activityEvent.onEach {
            when (it) {
                is MainViewModel.ActivityEvents.ShowLoginActivity -> {
                    startActivity(
                        Intent(
                            this,
                            LoginActivity::class.java
                        ).apply {
                            putExtra(LoginActivity.AVAILABLE, it.available)
                        }
                    )
                }
                is MainViewModel.ActivityEvents.ShowBookActivity -> {
                    startActivity(
                        Intent(
                            this,
                            BookActivity::class.java
                        )
                    )
                }
            }
        }.launchWhenCreated(lifecycleScope)
    }
}