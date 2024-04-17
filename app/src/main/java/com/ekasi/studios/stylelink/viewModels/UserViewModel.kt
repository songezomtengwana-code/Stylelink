package com.ekasi.studios.stylelink.viewModels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.ekasi.studios.stylelink.UserDetail
import com.ekasi.studios.stylelink.data.local.UserDao
import com.ekasi.studios.stylelink.data.model.ServerUserModel
import com.ekasi.studios.stylelink.data.serializer.userDetailDatastore
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UserViewModel(
    private val userDao: UserDao,
    private val application: Application
) :
    AndroidViewModel(application = application) {

    private fun provideApplicationContext(application: Application): Context =
        application.applicationContext

    var userDetailsId: String? = null


    init {
        viewModelScope.launch {
            val userDetailFlowUserId: Unit =
                provideApplicationContext(application).userDetailDatastore.data.map { preferences ->
                    preferences.userId
                }.collect{ formattedUserId ->
                    userDetailsId = formattedUserId
                    Log.v("userViewModel init{}", formattedUserId)
                }

            Log.v("userViewModel init{}", userDetailFlowUserId.toString())
        }
    }

    private val _loggedInUser = MutableStateFlow<FirebaseUser?>(null)
    private val _currentUser by lazy { MutableStateFlow<ServerUserModel?>(null) }

    // parser/container for data that will be received from data userdetail column
    private val _datastoreUserDetails = MutableStateFlow<UserDetail?>(null)
    val storedUser: LiveData<List<ServerUserModel>> = userDao.getAllUsers().asLiveData()
    val loggedInUser: StateFlow<FirebaseUser?> = _loggedInUser
    val serverUser: LiveData<ServerUserModel?> = _currentUser.asLiveData()

    // live value for datastore userdetail column response
    val dataStoreUserDetails: Flow<UserDetail?> = _datastoreUserDetails

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


    suspend fun setUserDetails(userId: String) {
//        TODO("implement write function to datastore and initiate all testing procedures")
        if (userId.isNotEmpty()) {
            viewModelScope.launch {
                provideApplicationContext(application).userDetailDatastore.updateData { userDetail ->
                    userDetail.toBuilder()
                        .setUserId(userId)
                        .build()
                }

                Log.v("setUserDetails", "new userId: $userId")
            }
        } else {
            Log.v("setUserDetails", "userId: $userId")
        }
    }

    suspend fun getUserDetails(): Flow<UserDetail>? {

        return try {
            val userDetailFlow: Flow<UserDetail> =
                provideApplicationContext(application).userDetailDatastore.data.map { userDetail ->
                    userDetail
                }

            userDetailFlow
        } catch (e: Exception) {
            Log.v("getUserDetails", e.localizedMessage as String)
            null
        }
    }
}

//data class UserDetailDatastoreModel(
////    val
//)