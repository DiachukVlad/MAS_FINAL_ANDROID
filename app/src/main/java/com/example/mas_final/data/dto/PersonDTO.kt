package com.example.mas_final.data.dto

open class PersonDTO(
    var name: String = "",
    var surname: String = "",
    var birthday: Long = 0L,
    var city: String = "",
    var phones: List<String> = listOf(),
    var email: String = "",
    var password: String = "",
    var token: TokenDTO? = null
) {
    override fun toString(): String {
        return "PersonDTO(name='$name', surname='$surname', birthday=$birthday, city='$city', phones=$phones, email='$email', password='$password', token=$token)"
    }
}
