package com.example.mas_final.data.dto

class ClientDTO(
    name: String = "",
    surname: String = "",
    birthday: Long = 0L,
    city: String = "",
    phones: List<String> = listOf(),
    email: String = "",
    password: String = "",
    var bonuses: Int? = null
): PersonDTO(name, surname, birthday, city, phones, email, password){

    var reservations: MutableList<ReservationDTO> = arrayListOf()

    override fun toString(): String {
        return super.toString() + " Client bonuses = $bonuses \n $reservations"
    }
}