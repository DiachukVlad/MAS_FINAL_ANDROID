package com.example.mas_final.data.dao

import com.example.mas_final.data.dto.VAResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonDao {
    @GET("api/accounts/checkEmail")
    suspend fun checkEmail(@Query(value = "email") email: String): VAResponseDTO<Boolean>?
}