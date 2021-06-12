package com.example.mas_final.data.dto


class ReservationDTO(
    var dateFrom: Long? = null,
    var dateTo: Long? = null,
    var token: TokenDTO? = null
) {
    var reservationObjects: MutableList<ReservationObject> = arrayListOf()
}

