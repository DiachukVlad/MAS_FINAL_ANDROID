package com.example.mas_final.viewLayers.views.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mas_final.data.dao.HelloDao
import com.example.mas_final.data.dto.PersonDTO
import com.example.mas_final.data.entity.VAResponse
import com.example.mas_final.databinding.ActivityMainBinding
import com.example.mas_final.helpers.Preferences
import com.example.mas_final.repositories.HelloRepository
import com.example.mas_final.viewLayers.views.register.RegisterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
	lateinit var binding: ActivityMainBinding

	private val prefs: Preferences by inject()
	private val helloRepo: HelloRepository by inject()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)

		setContentView(binding.root)


		startActivity(Intent(this, RegisterActivity::class.java))
	}
}