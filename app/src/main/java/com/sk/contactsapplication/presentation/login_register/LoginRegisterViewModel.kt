package com.sk.contactsapplication.presentation.login_register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sk.contactsapplication.R
import com.sk.contactsapplication.base.BaseResponse
import com.sk.contactsapplication.base.BaseViewModel
import com.sk.contactsapplication.data.repository.Repository
import com.sk.contactsapplication.data.request_body.LoginRegisterBody
import com.sk.contactsapplication.helpers.Constants
import com.sk.contactsapplication.helpers.Constants.EMAIL_REGEX_PATTERN
import com.sk.contactsapplication.helpers.UIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginRegisterViewModel @Inject constructor(val repository: Repository) : BaseViewModel() {

    private var _loginRegistrationUIState = MutableLiveData<LoginRegisterUIState>()
    val loginRegistrationUIState: LiveData<LoginRegisterUIState> = _loginRegistrationUIState

    private var isInLoggedInFlow: Boolean = true
    private var isApiCallInProgress: Boolean = false

    fun register(email: String, password: String) {
        if (isApiCallInProgress) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            if (email.trim().isNotEmpty() && password.trim().isNotEmpty()) {
                if (EMAIL_REGEX_PATTERN.matcher(email).matches()) {
                    if (netInfo.isInternetOn()) {
                        isApiCallInProgress = true
                        val loginRegisterBody = LoginRegisterBody(email = email, password = password)
                        val response = if (isInLoggedInFlow) {
                            repository.login(loginRegisterBody)
                        } else {
                            repository.register(loginRegisterBody)
                        }
                        when (response) {
                            is BaseResponse.Success -> {
                                val token = response.data.token
                                sessionManager.setString(Constants.AUTHORIZATION_TOKEN, token)
                                _loginRegistrationUIState.postValue(if (isInLoggedInFlow) {
                                    LoginRegisterUIState.LoginSuccess
                                } else {
                                    LoginRegisterUIState.RegistrationSuccess(UIText.StringResource(id = R.string.registration_success))
                                })
                                isApiCallInProgress = false
                                delay(100)
                                changeUI()
                            }

                            is BaseResponse.Error -> {
                                _loginRegistrationUIState.postValue(LoginRegisterUIState.Error(UIText.DynamicString(response.error)))
                                isApiCallInProgress = false
                            }
                        }
                    } else {
                        _loginRegistrationUIState.postValue(LoginRegisterUIState.Error(UIText.StringResource(id = R.string.no_internet)))
                    }
                } else {
                    _loginRegistrationUIState.postValue(LoginRegisterUIState.InvalidEmail(UIText.StringResource(id = R.string.invalid_email)))
                }
            } else {
                _loginRegistrationUIState.postValue(LoginRegisterUIState.EmptyInputFields(UIText.StringResource(id = R.string.empty_input_fields)))
            }
        }

    }

    fun changeUI() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isInLoggedInFlow) {
                isInLoggedInFlow = false
                _loginRegistrationUIState.postValue(LoginRegisterUIState.Register)
            } else {
                isInLoggedInFlow = true
                _loginRegistrationUIState.postValue(LoginRegisterUIState.Login)
            }
        }

    }

}