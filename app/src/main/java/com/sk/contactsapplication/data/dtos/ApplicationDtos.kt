package com.sk.contactsapplication.data.dtos

import com.google.gson.annotations.SerializedName

data class RegistrationDto(
    val id: Int? = null,
    val token: String? = null,
)

data class LoginDto(
    val token: String? = null,
)

data class UsersDto(
    @SerializedName("data")
    val usersList: List<User>? = null,
    val page: Int? = null,
    val per_page: Int? = null,
    val support: SupportDto? = null,
    val total: Int? = null,
    val total_pages: Int? = null,
)

data class User(
    val avatar: String? = null,
    val email: String? = null,
    val first_name: String? = null,
    val id: Int? = null,
    val last_name: String? = null,
)

data class SupportDto(
    val text: String? = null,
    val url: String? = null,
)

data class AddUserDto(
    val createdAt: String? = null,
    val id: String? = null,
    val job: String? = null,
    val name: String? = null,
)

