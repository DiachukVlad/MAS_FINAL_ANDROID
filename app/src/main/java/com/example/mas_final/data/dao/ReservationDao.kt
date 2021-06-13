package com.example.mas_final.data.dao

import com.example.mas_final.data.dto.*
import com.example.mas_final.data.requests.ReserveRequest
import com.example.mas_final.data.responses.ReservationsObjectsResponse
import com.google.gson.JsonArray
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ReservationDao {
    @GET("api/reservations/getObjects")
    suspend fun getReservationObjects(@Query(value = "dateFrom") dateFrom: Long, @Query(value = "dateTo") dateTo: Long): VAResponseDTO<ReservationsObjectsResponse>?

    @POST("api/reservations/book")
    suspend fun bookObjects(@Body reserveRequest: ReserveRequest): VAResponseDTO<String>
}