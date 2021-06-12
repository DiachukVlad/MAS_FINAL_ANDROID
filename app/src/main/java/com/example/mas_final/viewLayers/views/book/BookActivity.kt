package com.example.mas_final.viewLayers.views.book

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.mas_final.R
import com.example.mas_final.databinding.ActivityBookBinding
import com.example.mas_final.extentions.launchWhenStarted
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class BookActivity : AppCompatActivity() {
    lateinit var binding: ActivityBookBinding
    private val vm: BookViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(vm)

        vm.activityEvent.onEach {
            println(it)
        }.launchWhenStarted(lifecycleScope)
    }
}