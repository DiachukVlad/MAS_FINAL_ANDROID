package com.example.mas_final.viewLayers.views.book

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.util.Pair
import androidx.lifecycle.lifecycleScope
import com.example.mas_final.R
import com.example.mas_final.databinding.ActivityBookBinding
import com.example.mas_final.extentions.defScope
import com.example.mas_final.extentions.launchWhenCreated
import com.example.mas_final.extentions.launchWhenStarted
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class BookActivity : AppCompatActivity() {
    lateinit var binding: ActivityBookBinding
    private val vm: BookViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        observeActivityEvents()
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calendarButton.setOnClickListener { vm.onCalendarClick() }

        lifecycle.addObserver(vm)
    }

    private fun observeActivityEvents() {
        vm.activityEvent.onEach {
            when(it) {
                is BookViewModel.ActivityEvents.ShowDateRangePicker -> showDateRangePicker(it.from, it.to)
            }
        }.launchWhenCreated(lifecycleScope)
    }

    private fun showDateRangePicker(dateFrom: Long, dateTo: Long) {
        val dateRangePickerBuilder =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")

        if (dateFrom != 0L && dateTo != 0L) {
            dateRangePickerBuilder.setSelection(Pair(dateFrom, dateTo))
        }

        val datePicker = dateRangePickerBuilder.build()
        datePicker.show(supportFragmentManager, null)
        datePicker.addOnPositiveButtonClickListener {
            vm.dateFrom.value = it.first
            vm.dateTo.value = it.second

            vm.onDateRangeSelected()
        }
    }
}