package com.example.mas_final.data.dto


abstract class ReservationObject(open val name: String = "") {
    abstract var maxPeople: Int
    abstract var price: Int
}