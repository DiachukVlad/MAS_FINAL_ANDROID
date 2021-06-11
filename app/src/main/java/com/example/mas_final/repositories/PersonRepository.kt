package com.example.mas_final.repositories

import com.example.mas_final.data.dao.PersonDao
import com.example.mas_final.data.entity.VAResponse

class PersonRepository(private val personDao: PersonDao): BaseRepository() {
    suspend fun checkEmail(email: String) = catchCommonExceptions { personDao.checkEmail(email) }
}