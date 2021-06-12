package com.example.mas_final.viewLayers.views.main.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.example.mas_final.R
import com.example.mas_final.databinding.ObjectLayoutBinding
import com.example.mas_final.helpers.StringProvider
import com.example.mas_final.helpers.UTCHelper
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ReservationsAdapter(var reservations: List<Reservation>, val strings: StringProvider) :
    RecyclerView.Adapter<ReservationsAdapter.ReservationsViewHolder>() {

    class ReservationsViewHolder(private val binding: ObjectLayoutBinding, val strings: StringProvider) :
        RecyclerView.ViewHolder(binding.root) {
        var reservation: Reservation? = null
            set(value) {
                field = value
                if (value == null) return

                with(binding) {
                    with(reservation!!) {
                        image.setImageResource(imageRes)
                        title.text = name
                        priseText.text = strings.get(R.string.price_format).format(price)
                        val from: Date = Calendar.getInstance().apply { timeInMillis = UTCHelper().toLocal(dateFrom) }.time
                        val to: Date = Calendar.getInstance().apply { timeInMillis = UTCHelper().toLocal(dateTo) }.time
                        val dateFormat: DateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
                        subtitle.text = strings.get(R.string.reservation_range_format).format(dateFormat.format(from), dateFormat.format(to))
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ReservationsViewHolder {
        return ReservationsViewHolder(ObjectLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), strings)
    }

    override fun onBindViewHolder(holder: ReservationsViewHolder, position: Int) {
        holder.reservation = reservations[position]
    }

    override fun getItemCount() = reservations.size

    data class Reservation(val name: String, val dateFrom: Long, val dateTo: Long, val price: Int, @DrawableRes val imageRes: Int)
}