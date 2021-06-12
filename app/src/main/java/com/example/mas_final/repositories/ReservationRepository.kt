package com.example.mas_final.repositories

import com.example.mas_final.data.dao.ReservationDao
import com.example.mas_final.data.dto.ReservationObject
import com.example.mas_final.data.dto.VAResponseDTO
import com.example.mas_final.data.entity.Ok
import com.example.mas_final.data.entity.VAResponse
import com.google.gson.JsonArray

class ReservationRepository(val reservationDao: ReservationDao) : BaseRepository() {
    suspend fun getReservationObjects(dateFrom: Long, dateTo: Long) = catchCommonExceptions { reservationDao.getReservationObjects(dateFrom, dateTo) }
}