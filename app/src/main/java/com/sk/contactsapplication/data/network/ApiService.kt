package com.sk.contactsapplication.data.network

import com.sk.contactsapplication.data.dtos.AddUserDto
import com.sk.contactsapplication.data.dtos.LoginDto
import com.sk.contactsapplication.data.dtos.RegistrationDto
import com.sk.contactsapplication.data.dtos.UsersDto
import com.sk.contactsapplication.data.request_body.AddUserBody
import com.sk.contactsapplication.data.request_body.LoginRegisterBody
import com.sk.contactsapplication.helpers.Constants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {

    @POST(value = Constants.API_LOGIN)
    suspend fun login(@Body loginRegisterBody: LoginRegisterBody): LoginDto

    @POST(value = Constants.API_REGISTER)
    suspend fun register(@Body loginRegisterBody: LoginRegisterBody): RegistrationDto

    @GET(value = Constants.API_USERS)
    suspend fun getUsers(@Query("page") page: Int, @Query("per_page") perPage: Int): UsersDto

    @POST(value = Constants.API_USERS)
    suspend fun addUser(@Body addUserBody: AddUserBody): AddUserDto
}