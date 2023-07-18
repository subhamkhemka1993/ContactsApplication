package com.sk.contactsapplication.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sk.contactsapplication.base.BaseViewModel
import com.sk.contactsapplication.helpers.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): BaseViewModel() {

    private var _isValidUser = MutableLiveData<Boolean>()
    val isValidUser: LiveData<Boolean> = _isValidUser


    fun checkUserValidity() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            val authToken = sessionManager.getString(Constants.AUTHORIZATION_TOKEN)
            _isValidUser.postValue(authToken?.trim().isNullOrEmpty().not())
        }
    }

}