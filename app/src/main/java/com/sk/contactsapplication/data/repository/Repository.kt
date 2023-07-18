package com.sk.contactsapplication.data.repository

import com.sk.contactsapplication.base.BaseResponse
import com.sk.contactsapplication.data.request_body.AddUserBody
import com.sk.contactsapplication.data.request_body.LoginRegisterBody
import com.sk.contactsapplication.data.response_models.AddUserInfo
import com.sk.contactsapplication.data.response_models.LoginRegistrationUIInfo
import com.sk.contactsapplication.data.response_models.UserDataInfo

interface Repository {

    suspend fun login(loginRegisterBody: LoginRegisterBody): BaseResponse<LoginRegistrationUIInfo>

    suspend fun register(loginRegisterBody: LoginRegisterBody): BaseResponse<LoginRegistrationUIInfo>

    suspend fun getUsers(page: Int, pageSize: Int): BaseResponse<UserDataInfo>

    suspend fun addUser(addUserBody: AddUserBody): BaseResponse<AddUserInfo>
}