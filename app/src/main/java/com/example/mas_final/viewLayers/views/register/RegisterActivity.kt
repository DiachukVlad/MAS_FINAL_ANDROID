package com.example.mas_final.viewLayers.views.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mas_final.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}