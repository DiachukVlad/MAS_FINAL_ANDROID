package com.example.mas_final.data.dto


data class ReservationDTO(
    var dateFrom: Long? = null,
    var dateTo: Long? = null,
    var token: TokenDTO? = null,
    var rooms: MutableList<Room> = arrayListOf(),
    var houses: MutableList<House> = arrayListOf()
)
