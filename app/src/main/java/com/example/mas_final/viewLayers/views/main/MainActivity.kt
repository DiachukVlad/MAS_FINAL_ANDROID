package com.example.mas_final.viewLayers.views.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mas_final.data.dto.TokenDTO
import com.example.mas_final.databinding.ActivityMainBinding
import com.example.mas_final.helpers.Preferences
import com.example.mas_final.viewLayers.views.register.RegisterActivity
import org.koin.android.ext.android.inject
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val prefs: Preferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val token = prefs.token
        println(token)
//        if (
//            token == null ||
//            token.uuid.isEmpty() ||
//            token.expirationDate < Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis
//        ) {
            startActivity(Intent(this, RegisterActivity::class.java))
//        }
    }
}