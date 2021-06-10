package com.example.mas_final.data.dto

data class VAResponseDTO<T>(
    val error: VAError? = null,
    val body: T? = null
)

enum class VAError() {
    EmailExists,
    BadRequest,
    BadLogin,

    //Common
    UnexpectedError,
    ServerIsUnavailable,
    EmptyResponse
}