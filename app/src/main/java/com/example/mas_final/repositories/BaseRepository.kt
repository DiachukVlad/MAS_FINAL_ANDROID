package com.example.mas_final.repositories

import com.example.mas_final.data.dto.VAError
import com.example.mas_final.data.dto.VAResponseDTO
import com.example.mas_final.data.entity.Error
import com.example.mas_final.data.entity.Ok
import com.example.mas_final.data.entity.VAResponse
import com.example.mas_final.extentions.ioScope
import kotlinx.coroutines.withContext
import java.net.ConnectException

open class BaseRepository {
    suspend fun <T> catchCommonExceptions(call: suspend () -> VAResponseDTO<T>?): VAResponse<T> {
        try {
            val response = withContext(ioScope.coroutineContext) { call() }
                ?: return Error(VAError.EmptyResponse)

            val body = response.body ?: return Error(VAError.EmptyResponse)
            response.error.let {
                if (it != null) return Error(it)
            }

            return Ok(body)
        } catch (e: ConnectException) {
            return Error(VAError.ServerIsUnavailable)
        } catch (e: Exception) {
            println(e)
            return Error(VAError.UnexpectedError)
        }
    }
}