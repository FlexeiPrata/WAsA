package com.flexship.wordsassociations.common

import com.flexship.wordsassociations.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ErrorHandler(private val baseHandling: (Int) -> Unit) {
    suspend fun handleError(exception: Exception) {
        val message = when (exception) {
            is IOException -> R.string.no_internet_connection
            is HttpException -> {
                when (exception.code()) {
                    401 -> R.string.invalid_api_key
                    429 -> R.string.request_error
                    501 -> R.string.language_error
                    else -> R.string.general_http_error
                }
            }
            else -> R.string.generic_error
        }
        withContext(Dispatchers.Main) {
            baseHandling(message)
        }
    }
}