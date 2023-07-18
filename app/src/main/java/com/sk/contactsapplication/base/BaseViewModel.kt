package com.sk.contactsapplication.base

import androidx.lifecycle.ViewModel
import com.sk.contactsapplication.data.storage.SessionManager
import com.sk.contactsapplication.helpers.NetInfo
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    @Inject
    protected lateinit var netInfo: NetInfo

    @Inject
    protected lateinit var sessionManager: SessionManager
}