package com.example.mas_final.repositories

import com.example.mas_final.data.dao.PersonDao
import com.example.mas_final.data.dto.LoginInfoDTO
import com.example.mas_final.data.dto.PersonDTO
import com.example.mas_final.data.dto.TokenDTO
import com.example.mas_final.data.entity.VAResponse

class PersonRepository(private val personDao: PersonDao): BaseRepository() {
    suspend fun checkEmail(email: String) = catchCommonExceptions { personDao.checkEmail(email) }
    suspend fun register(person: PersonDTO) = catchCommonExceptions { personDao.register(person) }
    suspend fun loginToken(tokenDTO: TokenDTO) = catchCommonExceptions { personDao.loginToken(tokenDTO) }
    suspend fun login(loginInfoDTO: LoginInfoDTO) = catchCommonExceptions { personDao.login(loginInfoDTO) }
}