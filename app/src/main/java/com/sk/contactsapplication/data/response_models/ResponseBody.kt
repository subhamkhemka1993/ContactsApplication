package com.sk.contactsapplication.data.response_models

data class LoginRegistrationUIInfo(
    val token: String,
)

data class UserInfo(
    val avatar: String,
    val email: String,
    val name: String,
    var isSelected: Boolean = false
)

data class UserDataInfo(
    val userList: List<UserInfo>,
    val page: Int,
    val perPage: Int,
    val totalPages: Int,
)


data class AddUserInfo(
    val job: String,
    val name: String,
)
