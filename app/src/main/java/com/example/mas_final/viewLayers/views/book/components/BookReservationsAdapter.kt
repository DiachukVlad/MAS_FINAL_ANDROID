package com.example.mas_final.viewLayers.views.book.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.example.mas_final.R
import com.example.mas_final.databinding.ObjectLayoutBinding
import com.example.mas_final.helpers.StringProvider
import java.util.*


class BookReservationsAdapter(var reservations: List<Reservation>, private val strings: StringProvider) :
    RecyclerView.Adapter<BookReservationsAdapter.ReservationsViewHolder>() {

    var selectedReservations: HashSet<Reservation> = hashSetOf()

    class ReservationsViewHolder(private val binding: ObjectLayoutBinding, private val strings: StringProvider) :
        RecyclerView.ViewHolder(binding.root) {
        var onSelected: ((Boolean)->Unit)? = null

        var isSelected = false
            set(value) {
                field = value
                binding.mainContainer.setBackgroundResource(if (value) R.color.light_green else R.color.white)
                onSelected?.invoke(value)
            }

        var reservation: Reservation? = null
            set(value) {
                field = value
                if (value == null) return

                with(binding) {
                    with(reservation!!) {
                        image.setImageResource(imageRes)
                        title.text = name
                        priseText.text = strings.get(R.string.price_format).format(price)
                    }
                }
            }

        init {
            itemView.setOnClickListener {
                isSelected = !isSelected
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ReservationsViewHolder {
        return ReservationsViewHolder(ObjectLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), strings)
    }

    override fun onBindViewHolder(holder: ReservationsViewHolder, position: Int) {
        holder.reservation = reservations[position]
        holder.onSelected = {
            if (it) {
                selectedReservations.add(reservations[position])
            } else {
                selectedReservations.remove(reservations[position])
            }
        }
    }

    override fun getItemCount() = reservations.size

    data class Reservation(val name: String, val price: Int, @DrawableRes val imageRes: Int, val id: Int)
}