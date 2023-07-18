package com.sk.contactsapplication.data.error

import com.google.gson.annotations.SerializedName

data class ErrorUtils(
    @SerializedName("error")
    val message: String,
)

