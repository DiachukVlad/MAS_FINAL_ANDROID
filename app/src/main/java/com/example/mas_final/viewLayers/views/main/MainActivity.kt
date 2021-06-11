package com.example.mas_final.viewLayers.views.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mas_final.databinding.ActivityMainBinding
import com.example.mas_final.helpers.Preferences
import com.example.mas_final.viewLayers.views.register.RegisterActivity
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
	lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)

		setContentView(binding.root)


		startActivity(Intent(this, RegisterActivity::class.java))
	}
}