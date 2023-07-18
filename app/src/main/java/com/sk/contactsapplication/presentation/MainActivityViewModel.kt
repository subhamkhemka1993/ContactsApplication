package com.sk.contactsapplication.presentation

import com.sk.contactsapplication.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : BaseViewModel() {

    fun onLogout(){
        sessionManager.clear()
    }
}