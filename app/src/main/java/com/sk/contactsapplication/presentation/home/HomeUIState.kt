package com.sk.contactsapplication.presentation.home

import com.sk.contactsapplication.data.response_models.UserInfo
import com.sk.contactsapplication.helpers.UIText

sealed class HomeUIState {

    data class Users(val listOfUsers: List<UserInfo>) : HomeUIState()
    data class Error(val error: UIText) : HomeUIState()
}