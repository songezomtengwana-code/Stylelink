package com.ekasi.studios.stylelink.viewModels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ekasi.studios.stylelink.R
import com.ekasi.studios.stylelink.UserDetail
import com.ekasi.studios.stylelink.data.local.UserDao
import com.ekasi.studios.stylelink.data.model.ServerUserModel
import com.ekasi.studios.stylelink.data.repository.UserRepository
import com.ekasi.studios.stylelink.data.serializer.userDetailDatastore
import com.ekasi.studios.stylelink.helpers.KeystoreHelper
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UserViewModel(
    private val userDao: UserDao,
    private val application: Application,
    private val userRepository: UserRepository
) :
    AndroidViewModel(application = application) {

    private fun provideApplicationContext(application: Application): Context =
        application.applicationContext

    val context: Context = provideApplicationContext(application)

    private val sharedPreferences =
        context.getSharedPreferences(context.getString(R.string.pref_store), Context.MODE_PRIVATE)
    private val keystoreHelper = KeystoreHelper()


    var userDetailsId: String? = null

    init {
        viewModelScope.launch {
            val userDetailFlowUserId: Unit =
                provideApplicationContext(application).userDetailDatastore.data.map { preferences ->
                    preferences.token
                }.collect { formattedToken ->
                    userDetailsId = formattedToken
                }
            userDetailsId = userDetailFlowUserId.toString()
            Log.d("retrieveToken", "$userDetailFlowUserId")
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
            Log.d("userViewModel", e.message.toString())
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


    suspend fun setUserDetails(id: String,token: String) {
        viewModelScope.launch {
            try {
                provideApplicationContext(application).userDetailDatastore.updateData { userDetail ->
                    userDetail.toBuilder()
                        .setUserId(id)
                        .setToken(token)
                        .build()
                }

                Log.v("setUserDetails", "id: $id")
            } catch (e: Exception) {
                Log.v("setUserDetails", "${e.message}")
            }
        }
    }

    suspend fun storeToken(token: String) {
        viewModelScope.launch {
            try {
                provideApplicationContext(application).userDetailDatastore.updateData { userDetail ->
                    userDetail.toBuilder()
                        .setToken(token)
                        .build()
                }

                val result = retrieveToken()
                Log.d("setUserDetails", "User details updated: token=[$result]")
            } catch (e: Exception) {
                Log.v("setUserDetails", "${e.message}")
            }
        }
    }

    suspend fun retrieveToken(): String? {
        val context = provideApplicationContext(application)
        val dataStore = context.userDetailDatastore
        var tokenContainer: String? = null


        try {
            val token = dataStore.data.map { user ->
                user.token
            }.collect { formattedValue -> tokenContainer = formattedValue }.toString()

            Log.d("setUserDetails", "encryptedToken: $token")
            return token
        } catch (e: Exception) {
            return null;
        }


    }

    suspend fun getUserDetails(): Flow<String> {
        return provideApplicationContext(application).userDetailDatastore.data.map { user ->
            user.userId
        }
    }

    suspend fun fetchUser(id: String): ServerUserModel {
        return userRepository.getUserAccount(id)
    }

    fun fetchServerUser() {
        viewModelScope.launch {
            try {
                var pref = ""
                val userid =
                    provideApplicationContext(application).userDetailDatastore.data.map { user ->
                        user.userId
                    }.collect { preference ->
                        pref = preference
                    }.toString()

                Log.d("fetchServerUser", "initiating")
                if (userid.isNotEmpty()) {
                    val details = userRepository.getUserAccount(userid);
                    Log.d("fetchServerUser", "results: $details")

                } else {
                    Log.d("fetchServerUser", "userid: $userid")
                }
            } catch (e: Exception) {
                Log.d("fetchServerUser", "nullPointerException: ${e.message.toString()}")
            }
        }
    }

//    fun storeToken(token: String) {
//        viewModelScope.launch {
//            try {
//                val encryptedToken = keystoreHelper.encrypt(token)
//                if (encryptedToken != null) {
//                    with(sharedPreferences.edit()) {
//                        putString("bearer_token", encryptedToken)
//                        apply()
//                    }
//                }
//            } catch (e: Exception) {
//                Log.d("storeToken", e.message.toString())
//            }
//        }
//    }
//
//    fun retrieveToken(): String? {
//        val encryptedToken = sharedPreferences.getString("bearer_token", null)
//        return keystoreHelper.decrypt(encryptedToken ?: return null)
//    }

}