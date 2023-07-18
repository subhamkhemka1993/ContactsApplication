package com.sk.contactsapplication.data.repository

import com.sk.contactsapplication.base.BaseResponse
import com.sk.contactsapplication.data.dtos.toAddUserInfo
import com.sk.contactsapplication.data.dtos.toLoginRegistrationUIInfo
import com.sk.contactsapplication.data.dtos.toUserDataInfo
import com.sk.contactsapplication.data.error.ErrorHandler
import com.sk.contactsapplication.data.network.ApiService
import com.sk.contactsapplication.data.request_body.AddUserBody
import com.sk.contactsapplication.data.request_body.LoginRegisterBody
import com.sk.contactsapplication.data.response_models.AddUserInfo
import com.sk.contactsapplication.data.response_models.LoginRegistrationUIInfo
import com.sk.contactsapplication.data.response_models.UserDataInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val errorHandler: ErrorHandler,
) : Repository {
    override suspend fun login(loginRegisterBody: LoginRegisterBody): BaseResponse<LoginRegistrationUIInfo> {
        return try {
            val login = apiService.login(loginRegisterBody)
            BaseResponse.Success(login.toLoginRegistrationUIInfo())
        } catch (e: Exception) {
            BaseResponse.Error(errorHandler.getErrorType(e))
        }
    }

    override suspend fun register(loginRegisterBody: LoginRegisterBody): BaseResponse<LoginRegistrationUIInfo> {
        return try {
            val login = apiService.register(loginRegisterBody)
            BaseResponse.Success(login.toLoginRegistrationUIInfo())
        } catch (e: Exception) {
            BaseResponse.Error(errorHandler.getErrorType(e))
        }
    }

    override suspend fun getUsers(page: Int, pageSize: Int): BaseResponse<UserDataInfo> {
        return try {
            val users = apiService.getUsers(page = page, perPage = pageSize)
            BaseResponse.Success(users.toUserDataInfo())
        } catch (e: Exception) {
            BaseResponse.Error(errorHandler.getErrorType(e))
        }
    }

    override suspend fun addUser(addUserBody: AddUserBody): BaseResponse<AddUserInfo> {
        return try {
            val users = apiService.addUser(addUserBody)
            BaseResponse.Success(users.toAddUserInfo())
        } catch (e: Exception) {
            BaseResponse.Error(errorHandler.getErrorType(e))
        }
    }


}