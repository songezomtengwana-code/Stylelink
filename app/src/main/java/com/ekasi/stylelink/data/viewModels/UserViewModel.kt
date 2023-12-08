package com.ekasi.stylelink.data.viewModels

import androidx.lifecycle.ViewModel
import com.ekasi.stylelink.data.models.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel : ViewModel() {

    private val _loggedInUser = MutableStateFlow<UserModel?>(null)
    val loggedInUser: StateFlow<UserModel?> = _loggedInUser

    fun saveLoggedInUser(user: UserModel?) {
        _loggedInUser.value = user
    }

    fun getLoggedInUserData(): UserModel? {
        return _loggedInUser.value
    }

    fun clearLoggedInUser() {
        _loggedInUser.value = null
    }
}
