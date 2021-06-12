package com.example.mas_final.data.dto


class House(
    var rooms: Int = 0,
    var kitchens: Int = 0,
    var bathrooms: Int = 0,
    name: String = ""
): ReservationObject(name) {
    override var maxPeople: Int = 0
    override var price: Int = 0
    override fun toString(): String {
        return "House(id=$id, name=$name, rooms=$rooms, kitchens=$kitchens, bathrooms=$bathrooms, maxPeople=$maxPeople, price=$price)"
    }


}