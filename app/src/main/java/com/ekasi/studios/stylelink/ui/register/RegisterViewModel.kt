package com.ekasi.studios.stylelink.ui.register

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ekasi.studios.stylelink.data.model.RegistrationUserModel
import com.ekasi.studios.stylelink.data.model.ServerUserModel
import com.ekasi.studios.stylelink.data.repository.AuthRepository
import com.ekasi.studios.stylelink.data.repository.UserRepository
import com.ekasi.studios.stylelink.navigation.Screen
import com.ekasi.studios.stylelink.ui.splash.MainScreen
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(
    private val repository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    var isLoading by mutableStateOf(false)
    var isSnackBarVisible by mutableStateOf(false)
    var navigateToMain by mutableStateOf(false)
    private val _userLiveData = MutableLiveData<FirebaseUser?>()
    private val _userServerData = MutableLiveData<ServerUserModel?>()
    val userLiveData: LiveData<FirebaseUser?> get() = _userLiveData
    val userServerLiveData: LiveData<ServerUserModel?> get() = _userServerData



    private val _errorMessageLiveData = MutableLiveData<String?>()
    val errorMessageLiveData: LiveData<String?> get() = _errorMessageLiveData


    fun authenticateUserAccount(email: String, password: String, navController: NavController) {

        if (email.isNotEmpty() || password.isNotEmpty()) {
            Log.d("triggerRegisterUser", "all fields are filled")
            isLoading = true
            // initiating server sign in

            viewModelScope.launch {
                try {
                    val result = repository.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        task ->

                        if (task.isSuccessful) {
                            isLoading = false
                            navigateToMain = true
                        } else {
                            isLoading = false
                        }
                    }
                    _userLiveData.value = result.result.user
                    Log.d(
                        "AuthResponse", result.result.user.toString()
                    )

                } catch (e: Exception) {
                    _errorMessageLiveData.value = e.localizedMessage
                    e.localizedMessage?.let { Log.d("registerScreenAuthError", it.toString()) }
                    isLoading = false
                }
            }
        } else {
            Log.d("triggerRegisterUser", "one of the fields is not field")
        }
    }

    fun registerUserAccount(
        fullname: String,
        email: String,
        password: String,
        phoneNumber: String,
        navController: NavController
    ) {
        isLoading = true
        Log.d("registerUserAccount", "Starting Registration Service")
        val newUserAccount = RegistrationUserModel(
            fullname = fullname,
            email = email,
            password = password,
            phoneNumber = phoneNumber
        )
        viewModelScope.launch {
            try {
                Log.d("registerUserAccount", "Initiating Registration Service")
                val createdUser = userRepository.createUserAccount(newUserAccount)
//                _userServerData.value = createdUser

                createdUser.enqueue(object : Callback<ServerUserModel> {
                    override fun onResponse(call: Call<ServerUserModel>, response: Response<ServerUserModel>) {
                        if (response.isSuccessful) {
                            val user = response.body()
                            _userServerData.value = user
                            Log.d("registerUserAccount", user.toString())
//                            authenticateUserAccount(email,password,navController)
//                            navController.navigate(Screen.Login.route)

                        } else {
                            Log.d("registerUserAccount", response.message())
                            isLoading = false
                            isSnackBarVisible = true
                        }
                    }

                    override fun onFailure(call: Call<ServerUserModel>, t: Throwable) {
                        isLoading = false
                        isSnackBarVisible = true
                        Log.d("registerUserAccount", t.message.toString())
                    }
                })

//                navController.navigate(Screen.Main.route)

//                try {
//                    authenticateUserAccount(email, password, navController)
//                    Log.d("registerUserAccount", createdUser.toString())
//
//                } catch (e: Exception) {
//                    _errorMessageLiveData.value = e.localizedMessage
////                    e.localizedMessage?.let { Log.d("registerScreenErorr", it.toString()) }
//                    isLoading = false
//                    Log.d("registerAccountError", "Error Encountered: 98")
//                }

            } catch (e: Exception) {
                _errorMessageLiveData.value = e.localizedMessage
                isLoading = false
            }
        }
    }
}