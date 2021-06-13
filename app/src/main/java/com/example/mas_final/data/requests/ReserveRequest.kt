package com.example.mas_final.data.requests

import com.example.mas_final.data.dto.TokenDTO


data class ReserveRequest(
    val ids: List<Int>,
    val dateFrom: Long,
    val dateTo: Long,
    val token: TokenDTO
)
