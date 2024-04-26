package com.fenil.bookshelfapp.DelayAwareClickListener

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

data class Resource<T>(var status: Status, var data: T?, val message: String?, val statusCode: Int) {
    companion object {
        fun <T> success(data: T?, statusCode: Int = HttpURLConnection.HTTP_OK): Resource<T> {
            return Resource(Status.SUCCESS, data, null, statusCode)
        }

        fun <T> error(msg: String, data: T?, statusCode: Int = HttpURLConnection.HTTP_INTERNAL_ERROR): Resource<T> {
            return Resource(Status.ERROR, data, msg, statusCode = statusCode)
        }

        fun <T> failure(statusCode: Int = HttpURLConnection.HTTP_INTERNAL_ERROR): Resource<T> {
            return Resource(Status.FAILURE, null, null, statusCode = statusCode)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null, statusCode = HttpURLConnection.HTTP_OK)
        }
        fun <T> noInternet(): Resource<T> {
            return Resource(Status.NO_INTERNET, null, null, statusCode = HttpURLConnection.HTTP_CLIENT_TIMEOUT)
        }
    }

    fun <R> map(transform: (data: T) -> R?): Resource<R> {
        return Resource(status, data?.let(transform), message, statusCode)
    }
}

enum class Status {
    LOADING,
    SUCCESS,
    ERROR,
    FAILURE,
    NO_INTERNET
}

suspend fun <T> Call<T>.asResource(): Resource<T> = suspendCoroutine { continuation ->
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val responseData = if (response.isSuccessful) {
                Resource.success(response.body(), response.code())
            } else {
                Resource.error<T>(response.errorBody()?.string() ?: response.message(), null, response.code())
            }
            continuation.resume(responseData)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            continuation.resume(Resource.error(t.message.orEmpty(), null))
        }
    })
}