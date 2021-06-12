package com.example.mas_final.data.dto


abstract class ReservationObject(open val name: String = "") {
    var id: Int = 0

    abstract var maxPeople: Int
    abstract var price: Int
}