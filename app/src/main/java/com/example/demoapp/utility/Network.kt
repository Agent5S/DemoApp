package com.example.demoapp.utility

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed class Result<out T> {
    data class Success<out T>(val value: T): Result<T>()
    data class GenericError(val code: Int? = null): Result<Nothing>()
    object NetworkError: Result<Nothing>()
}

suspend fun <T> safeAPICall(dispatcher: CoroutineDispatcher = Dispatchers.IO, apiCall: suspend () -> T): Result<T> {
    return withContext(dispatcher) {
        try {
            Result.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> Result.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    Result.GenericError(code)
                }
                else -> {
                    Result.GenericError(null)
                }
            }
        }
    }
}