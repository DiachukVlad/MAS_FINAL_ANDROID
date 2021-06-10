package com.example.mas_final.repositories

import com.example.mas_final.data.dto.VAError
import com.example.mas_final.data.dto.VAResponseDTO
import com.example.mas_final.data.entity.VAResponse
import java.net.ConnectException

open class BaseRepository {
    suspend fun <T> catchCommonExceptions(call: suspend () -> VAResponseDTO<T>?): VAResponse<T> {
        try {
            val response = call() ?: return VAResponse.OnError(VAError.EmptyResponse)
            val body = response.body ?: return VAResponse.OnError(VAError.EmptyResponse)
            response.error.let {
                if (it != null) return VAResponse.OnError(it)
            }

            return VAResponse.OnOk(body)
        } catch (e: ConnectException) {
            return VAResponse.OnError(VAError.ServerIsUnavailable)
        } catch (e: Exception) {
            println(e)
            return VAResponse.OnError(VAError.UnexpectedError)
        }
    }
}