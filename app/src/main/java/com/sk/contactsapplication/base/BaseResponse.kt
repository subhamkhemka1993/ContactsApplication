package com.sk.contactsapplication.base

sealed class BaseResponse<T> {

    data class Success<T>(val data: T) : BaseResponse<T>()
    data class Error<T>(val error: String) : BaseResponse<T>()
}