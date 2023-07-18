package com.sk.contactsapplication.presentation.add_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sk.contactsapplication.R
import com.sk.contactsapplication.base.BaseResponse
import com.sk.contactsapplication.base.BaseViewModel
import com.sk.contactsapplication.data.repository.Repository
import com.sk.contactsapplication.data.request_body.AddUserBody
import com.sk.contactsapplication.data.request_body.LoginRegisterBody
import com.sk.contactsapplication.helpers.Constants
import com.sk.contactsapplication.helpers.UIText
import com.sk.contactsapplication.presentation.login_register.LoginRegisterUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private var _addUserUIState = MutableLiveData<AddUserUIState>()
    val addUserUIState: LiveData<AddUserUIState> = _addUserUIState

    private var isApiCallInProgress: Boolean = false


    fun addUser(name: String, job: String) {
        if (isApiCallInProgress) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            if (name.trim().isNotEmpty() && job.trim().isNotEmpty()) {
                if (netInfo.isInternetOn()) {
                    isApiCallInProgress = true
                    val addUserBody = AddUserBody(name = name, job = job)
                    val response = repository.addUser(addUserBody)
                    when (response) {
                        is BaseResponse.Success -> {
                            _addUserUIState.postValue(AddUserUIState.UserAddedSuccessfully(UIText.StringResource(id = R.string.user_added_successfully)))
                            isApiCallInProgress = false
                        }

                        is BaseResponse.Error -> {
                            _addUserUIState.postValue(AddUserUIState.Error(UIText.DynamicString(response.error)))
                            isApiCallInProgress = false
                        }
                    }
                } else {
                    _addUserUIState.postValue(AddUserUIState.Error(UIText.StringResource(id = R.string.no_internet)))
                }
            } else {
                _addUserUIState.postValue(AddUserUIState.EmptyInputFields(UIText.StringResource(id = R.string.empty_input_fields)))
            }
        }

    }

}