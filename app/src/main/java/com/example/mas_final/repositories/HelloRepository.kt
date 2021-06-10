package com.example.mas_final.repositories

import com.example.mas_final.data.dao.HelloDao
import com.example.mas_final.data.dto.PersonDTO
import com.example.mas_final.data.entity.VAResponse

class HelloRepository(private val helloDao: HelloDao) : BaseRepository() {
    suspend fun hello(): VAResponse<PersonDTO> = catchCommonExceptions { helloDao.hello() }
}