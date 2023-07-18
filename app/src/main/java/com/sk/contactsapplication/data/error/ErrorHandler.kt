package com.sk.contactsapplication.data.error

interface ErrorHandler {
    fun getErrorType(throwable: Throwable): String
}