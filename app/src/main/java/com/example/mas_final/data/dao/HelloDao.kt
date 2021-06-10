package com.example.mas_final.data.dao

import com.example.mas_final.data.dto.PersonDTO
import com.example.mas_final.data.dto.VAResponseDTO
import retrofit2.http.GET

interface HelloDao {
    @GET("hello")
    suspend fun hello(): VAResponseDTO<PersonDTO>?
}