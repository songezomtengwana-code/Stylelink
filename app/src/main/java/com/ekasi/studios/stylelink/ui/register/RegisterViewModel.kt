package com.ekasi.studios.stylelink.ui.register

import android.util.Log
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
import com.ekasi.studios.stylelink.data.repository.UserRepository
import com.ekasi.studios.stylelink.navigation.Screen
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import retrofit2.Call

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val navController: NavController
) : ViewModel() {
    var isLoading: Boolean by mutableStateOf(true)
    var isSnackBarVisible by mutableStateOf(false)
    private val _userLiveData = MutableLiveData<FirebaseUser?>()
    private val _userServerData = MutableLiveData<Call<ServerUserModel>?>()
    val userLiveData: LiveData<FirebaseUser?> get() = _userLiveData
    val userServerLiveData: LiveData<Call<ServerUserModel>?> get() = _userServerData


    private val _errorMessageLiveData = MutableLiveData<String?>()
    val errorMessageLiveData: LiveData<String?> get() = _errorMessageLiveData

    fun registerUserAccount(
        fullname: String,
        email: String,
        password: String,
        phoneNumber: String
    ) {
        showLoading()
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
                val response = userRepository.createUserAccount(newUserAccount)

                if (response.isExecuted) {
                    val user = response  // Use !! for non-null assertion after successful response
                    _userServerData.value = user
                    Log.d("registerUserAccount", user.toString())
                    navigateTo(Screen.Main.route)
                    dismissLoading()
                }
            } catch (e: Exception) {
                _errorMessageLiveData.value = e.localizedMessage
                activateSnackBar("Sorry, there was a problem with creating your account")
               dismissLoading()
            }
        }
    }

    private fun navigateTo(route: String) {
        navController.navigate(route)
    }

    private fun showLoading() {
        isLoading = true
    }

    private fun dismissLoading() {
        isLoading = false
    }

    fun activateSnackBar(message: String): String {
        isSnackBarVisible = true
        return message
    }
}