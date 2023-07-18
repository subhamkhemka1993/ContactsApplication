package com.sk.contactsapplication.presentation.add_user

import com.sk.contactsapplication.helpers.UIText

sealed class AddUserUIState {
    data class UserAddedSuccessfully(val message: UIText) : AddUserUIState()
    data class Error(val error: UIText) : AddUserUIState()
    data class EmptyInputFields(val error: UIText) : AddUserUIState()
}