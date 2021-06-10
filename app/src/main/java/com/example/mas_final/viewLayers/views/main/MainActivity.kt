package com.example.mas_final.viewLayers.views.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mas_final.data.dao.HelloDao
import com.example.mas_final.data.dto.PersonDTO
import com.example.mas_final.data.entity.VAResponse
import com.example.mas_final.databinding.ActivityMainBinding
import com.example.mas_final.helpers.Preferences
import com.example.mas_final.repositories.HelloRepository
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


		binding.test.setOnClickListener {
			CoroutineScope(Dispatchers.IO).launch {
				when(val res = helloRepo.hello()) {
					is VAResponse.OnOk -> {
						println(res.res)
					}
					is VAResponse.OnError -> {
						println(res.error)
					}
				}
			}
		}
	}
}