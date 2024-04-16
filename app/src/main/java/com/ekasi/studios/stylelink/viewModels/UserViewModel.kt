package com.ekasi.studios.stylelink.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ekasi.studios.stylelink.data.local.UserDao
import com.ekasi.studios.stylelink.data.model.ServerUserModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao) : ViewModel() {

    private val _loggedInUser = MutableStateFlow<FirebaseUser?>(null)
    private val _currentUser = MutableStateFlow<ServerUserModel?>(null)
    val storedUser: LiveData<List<ServerUserModel>> = userDao.getAllUsers().asLiveData()
    val loggedInUser: StateFlow<FirebaseUser?> = _loggedInUser
    val serverUser: LiveData<ServerUserModel?> = _currentUser.asLiveData()

//    fun saveLoggedInUser(user: ServerUserModel) = viewModelScope.launch {
//        userDao.insert(user)
//    }

    suspend fun storeCurrentUser(user: ServerUserModel) {
        userDao.insert(user)
        _currentUser.value = user
        Log.d("registerUserAccount", "currentUser: $serverUser")

    }

    fun getCurrentUser(): List<ServerUserModel>? {
        return try {
            val users = userDao.getAllUsers().asLiveData()
            Log.d("userViewModel", users.value.toString())

            //            return storedUser.value
            users.value
        } catch (e: Exception) {
            Log.d("userViewModel", e.localizedMessage)
            null
        }
    }

    suspend fun getServerUser(): ServerUserModel? {
        return serverUser.value
    }

    suspend fun getAuthUser(): FirebaseUser? {
        return try {
            val authUser = Firebase.auth.currentUser

            if (authUser !== null) {
                _loggedInUser.value = authUser
                loggedInUser.value
            } else {
                Log.v("getAuthUserException", authUser?.email ?: "no email was returned")
                null
            }
        } catch (e: Exception) {
            Log.v("getAuthUserException", e.localizedMessage)
            null
        }
    }

    suspend fun clearCurrentUser() {
        _currentUser.value = null
    }

    fun getLoggedInUserData(): List<ServerUserModel>? {
        return storedUser.value
    }

    fun clearLoggedInUser() {
        _loggedInUser.value = null
    }
}
