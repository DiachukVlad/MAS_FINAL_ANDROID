package com.example.mas_final.data.dto


class Room(
    var kitchen: Boolean = false,
    var bathroom: Boolean = false,
    var refrigerator: Boolean = false
) : ReservationObject() {
    override var maxPeople: Int = 0
    override var price: Int = 0
}