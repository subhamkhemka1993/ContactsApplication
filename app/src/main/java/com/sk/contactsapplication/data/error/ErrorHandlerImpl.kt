package com.sk.contactsapplication.data.error

import com.google.gson.Gson
import com.sk.contactsapplication.helpers.Constants
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

class ErrorHandlerImpl @Inject constructor(private val gson: Gson) : ErrorHandler {

    override fun getErrorType(throwable: Throwable): String {
        return when (throwable) {
            is IOException -> {
                Constants.NETWORK_ERROR
            }

            is HttpException -> {
                return when (throwable.code()) {
                    HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                        Constants.NETWORK_ERROR
                    }

                    else -> {
                        val response = throwable.response()
                        val error = response?.errorBody()?.string()
                        error?.let {
                            val errorUtils = gson.fromJson(it, ErrorUtils::class.java)
                            errorUtils.message
                        } ?: Constants.NETWORK_ERROR
                    }
                }
            }

            else -> {
                Constants.NETWORK_ERROR
            }
        }
    }
}