package com.sk.contactsapplication.data.request_body

data class LoginRegisterBody(val email: String, val password: String)

data class AddUserBody(val name: String, val job: String)