package com.flexship.wordsassociations.common

import android.content.Context
import android.widget.Toast
import com.flexship.wordsassociations.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.ref.WeakReference
import java.net.UnknownHostException

class ErrorHandler(context: Context, val errorCallback: (Error) -> Unit) {

    private val context = WeakReference(context)

    suspend fun handleError(exception: Exception) {
        val message = when (exception) {
            is UnknownHostException -> R.string.no_internet_connection
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
            context.get()?.let { fragmentContext ->
                Toast.makeText(fragmentContext, fragmentContext.getString(message), Toast.LENGTH_LONG).show()
                errorCallback(Error.MessageError)
            }
        }
    }
}

enum class Error {
    MessageError
}