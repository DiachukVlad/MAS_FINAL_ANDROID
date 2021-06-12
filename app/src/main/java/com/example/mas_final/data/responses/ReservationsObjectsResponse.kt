package com.example.mas_final.data.responses

import com.example.mas_final.data.dto.House
import com.example.mas_final.data.dto.Room

data class ReservationsObjectsResponse(
    var houses: MutableList<House> = arrayListOf(),
    var rooms: MutableList<Room> = arrayListOf()
)