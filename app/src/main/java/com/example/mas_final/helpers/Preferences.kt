package com.example.mas_final.helpers

import com.example.mas_final.data.dto.TokenDTO

class Preferences(private val strings: StringProvider)  {
    var token: TokenDTO? by PrefsDelegate<TokenDTO?>("token", null, TokenDTO::class)
}