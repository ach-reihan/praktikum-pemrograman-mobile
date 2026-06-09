package com.movietablecompose.util

import timber.log.Timber

sealed interface ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>
    data class Error(val message: String, val throwable: Throwable? = null) : ApiResult<Nothing>
    data object Loading : ApiResult<Nothing>
}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall())
    } catch (e: Exception) {
        Timber.e(e, "Error saat melakukan safeApiCall")
        ApiResult.Error(e.localizedMessage ?: "Terjadi kesalahan jaringan", e)
    }
}