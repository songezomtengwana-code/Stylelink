package com.ekasi.studios.stylelink.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ekasi.studios.stylelink.data.local.UserDao
import com.ekasi.studios.stylelink.data.local.UserEntity
import com.ekasi.studios.stylelink.data.model.ServerUserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao) : ViewModel() {

    private val _loggedInUser = MutableStateFlow<UserEntity?>(null)
    val storedUser: LiveData<List<UserEntity>> = userDao.getAllUsers().asLiveData()
    val loggedInUser: StateFlow<UserEntity?> = _loggedInUser

    fun saveLoggedInUser(user: UserEntity) = viewModelScope.launch {
        userDao.insert(user)
    }

    fun getLoggedInUserData(): List<UserEntity>? {
        return storedUser.value
    }

    fun clearLoggedInUser() {
        _loggedInUser.value = null
    }
}
