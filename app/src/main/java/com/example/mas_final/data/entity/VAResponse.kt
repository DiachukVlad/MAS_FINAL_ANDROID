package com.example.mas_final.data.entity

import com.example.mas_final.data.dto.VAError

sealed class VAResponse<T> {
    class OnOk<T>(val res: T) : VAResponse<T>()
    class OnError<T>(val error: VAError) : VAResponse<T>()
}
