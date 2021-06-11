package com.example.mas_final.data.entity

import com.example.mas_final.data.dto.VAError

sealed class VAResponse<T>

data class Ok<T>(val body: T) : VAResponse<T>()
data class Error<T>(val error: VAError) : VAResponse<T>()