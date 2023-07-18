package com.sk.contactsapplication.presentation.home_fragment

import com.sk.contactsapplication.data.response_models.UserInfo
import com.sk.contactsapplication.helpers.UIText

sealed class HomeUIState {

    data class Users(val listOfUsers: List<UserInfo>) : HomeUIState()
    data class Error(val error: UIText) : HomeUIState()
}