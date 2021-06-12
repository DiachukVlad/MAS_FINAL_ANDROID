package com.example.mas_final.data.dto


class Room(
    var kitchen: Boolean = false,
    var bathroom: Boolean = false,
    var refrigerator: Boolean = false,
    name: String = ""
) : ReservationObject() {
    override var maxPeople: Int = 0
    override var price: Int = 0

    override fun toString(): String {
        return "Room(name=$name, kitchen=$kitchen, bathroom=$bathroom, refrigerator=$refrigerator, maxPeople=$maxPeople, price=$price)"
    }
}