package com.sk.contactsapplication.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sk.contactsapplication.R
import com.sk.contactsapplication.base.BaseResponse
import com.sk.contactsapplication.base.BaseViewModel
import com.sk.contactsapplication.data.repository.Repository
import com.sk.contactsapplication.data.response_models.UserInfo
import com.sk.contactsapplication.helpers.UIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private var _pageSize: Int = 6
    val pageSize: Int
        get() = _pageSize

    private var _isLoading: Boolean = false
    val isLoading: Boolean
        get() = _isLoading
    private var _isLastPage: Boolean = false
    val isLastPage: Boolean
        get() = _isLastPage

    private var currentPage: Int = 1

    private val listOfUsers = arrayListOf<UserInfo>()

    private var _homeUIState = MutableLiveData<HomeUIState>()
    val homeUIState: LiveData<HomeUIState> = _homeUIState

    private var _lastSelectedPosition: Int = -1

    fun fetchUsers() {
        if (_isLoading) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            if (netInfo.isInternetOn()) {
                _isLoading = true
                val users = repository.getUsers(page = currentPage, pageSize = pageSize)
                when (users) {
                    is BaseResponse.Success -> {
                        listOfUsers.addAll(users.data.userList)
                        _isLastPage = currentPage == users.data.totalPages
                        _homeUIState.postValue(HomeUIState.Users(listOfUsers))
                        currentPage = currentPage.plus(1)
                    }

                    is BaseResponse.Error -> {
                        _homeUIState.postValue(HomeUIState.Error(UIText.DynamicString(users.error)))
                    }
                }
                _isLoading = false
            } else {
                _homeUIState.postValue(HomeUIState.Error(UIText.StringResource(id = R.string.no_internet)))
            }
        }
    }

    fun onItemTapped(itemPosition: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_lastSelectedPosition != -1) {
                if (_lastSelectedPosition == itemPosition) {
                    return@launch
                }
                val userInfo = listOfUsers[_lastSelectedPosition]
                val lastSelectedUserInfo = userInfo.copy(isSelected = false)
                listOfUsers[_lastSelectedPosition] = lastSelectedUserInfo
                setItemSelected(itemPosition)
            } else {
                setItemSelected(itemPosition)
            }
        }
    }

    private fun setItemSelected(itemPosition: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userInfo = listOfUsers[itemPosition]
            val currentUserInfo = userInfo.copy(isSelected = true)
            listOfUsers[itemPosition] = currentUserInfo
            _lastSelectedPosition = itemPosition
            _homeUIState.postValue(HomeUIState.Users(listOfUsers))
        }
    }
}