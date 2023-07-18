package com.sk.contactsapplication.data.dtos

import com.sk.contactsapplication.data.response_models.AddUserInfo
import com.sk.contactsapplication.data.response_models.LoginRegistrationUIInfo
import com.sk.contactsapplication.data.response_models.UserDataInfo
import com.sk.contactsapplication.data.response_models.UserInfo
import com.sk.contactsapplication.helpers.orZero


fun RegistrationDto.toLoginRegistrationUIInfo(): LoginRegistrationUIInfo {
    return LoginRegistrationUIInfo(token = token.orEmpty())
}

fun LoginDto.toLoginRegistrationUIInfo(): LoginRegistrationUIInfo {
    return LoginRegistrationUIInfo(token = token.orEmpty())
}

fun User.toUserInfo(): UserInfo {
    return UserInfo(
        avatar = avatar.orEmpty(),
        email = email.orEmpty(),
        name = if (first_name.isNullOrEmpty()) {
            if (last_name.isNullOrEmpty()) {
                ""
            } else {
                last_name
            }
        } else {
            if (last_name.isNullOrEmpty()) {
                first_name
            } else {
                "$first_name $last_name"
            }
        }

    )
}

fun UsersDto.toUserDataInfo(): UserDataInfo {
    return UserDataInfo(
        userList = usersList?.map { it.toUserInfo() } ?: emptyList(),
        page = page.orZero(),
        perPage = per_page.orZero(),
        totalPages = total_pages.orZero()
    )
}

fun AddUserDto.toAddUserInfo(): AddUserInfo {
    return AddUserInfo(
        job = job.orEmpty(),
        name = name.orEmpty()
    )
}
