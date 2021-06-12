package com.example.mas_final.helpers

import com.example.mas_final.data.dto.ClientDTO
import com.example.mas_final.data.dto.PersonDTO
import com.example.mas_final.data.dto.TokenDTO

class Preferences(private val strings: StringProvider)  {
    var token: TokenDTO? by PrefsDelegate("token", null, TokenDTO::class)
    var client: PersonDTO? by PrefsDelegate("client", null, ClientDTO::class)
}