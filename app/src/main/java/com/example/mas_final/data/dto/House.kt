package com.example.mas_final.data.dto


data class House(
    var rooms: Int = 0,
    var kitchens: Int = 0,
    var bathrooms: Int = 0
): ReservationObject() {
    override var maxPeople: Int = 0
    override var price: Int = 0
}