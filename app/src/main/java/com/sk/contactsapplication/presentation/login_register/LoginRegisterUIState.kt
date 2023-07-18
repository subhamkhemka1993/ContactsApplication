package com.sk.contactsapplication.presentation.login_register

import com.sk.contactsapplication.helpers.UIText

sealed class LoginRegisterUIState {
    object Register : LoginRegisterUIState()
    object Login : LoginRegisterUIState()
    object LoginSuccess : LoginRegisterUIState()
    data class RegistrationSuccess(val message: UIText) : LoginRegisterUIState()
    data class Error(val error: UIText) : LoginRegisterUIState()
    data class EmptyInputFields(val error: UIText) : LoginRegisterUIState()
    data class InvalidEmail(val error: UIText) : LoginRegisterUIState()
}