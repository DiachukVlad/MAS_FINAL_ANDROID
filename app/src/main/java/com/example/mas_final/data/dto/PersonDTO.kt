package com.example.mas_final.data.dto

data class PersonDTO(
    var name: String = "",
    var surname: String = "",
    var birthday: Long = 0L,
    var city: String = "",
    var phones: List<String> = listOf(),
    var email: String = "",
    var password: String = "",
    var token: TokenDTO? = null
)
