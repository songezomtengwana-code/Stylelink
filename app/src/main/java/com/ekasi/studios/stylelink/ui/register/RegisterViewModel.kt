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
import com.ekasi.studios.stylelink.data.model.ServerUserModel
import com.ekasi.studios.stylelink.data.repository.AuthRepository
import com.ekasi.studios.stylelink.data.repository.UserRepository
import com.ekasi.studios.stylelink.navigation.Screen
import com.ekasi.studios.stylelink.ui.splash.MainScreen
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository, private val userRepository: UserRepository): ViewModel() {
    var isLoading by mutableStateOf(false)
    private val _userLiveData = MutableLiveData<FirebaseUser?>()
    private val _userServerData = MutableLiveData<ServerUserModel?> ()
    val userLiveData: LiveData<FirebaseUser?> get() = _userLiveData
    val userServerLiveData: LiveData<ServerUserModel?> get() = _userServerData


    private val _errorMessageLiveData = MutableLiveData<String?>()
    val errorMessageLiveData: LiveData<String?> get() = _errorMessageLiveData


    fun authenticateUserAccount(email: String, password: String) {

        if (email.isNotEmpty() || password.isNotEmpty()) {
            Log.d("triggerRegisterUser", "all fields are filled")
            isLoading = true
            // initiating server sign in

            viewModelScope.launch {
                try {
                    val result = repository.createUserWithEmailAndPassword(email, password)
                    _userLiveData.value = result.result.user
                } catch (e: Exception) {
                    _errorMessageLiveData.value = e.localizedMessage
                    e.localizedMessage?.let { Log.d("registerScreenAuthError", it.toString()) }
                }
            }
        } else {
            Log.d("triggerRegisterUser", "one of the fields is not field")
        }
    }

    fun registerUserAccount(fullname: String, email: String, password: String, phoneNumber: String, navController: NavController) {
        val newUserAccount =  ServerUserModel(
            fullname= fullname,
            email = email,
            password = password,
            phoneNumber = phoneNumber
        )
        viewModelScope.launch {
            try {
                val createdUser = userRepository.createUserAccount(newUserAccount)
                _userServerData.value = createdUser

                try {
                    authenticateUserAccount(email, password)
                    navController.navigate(Screen.Main.route)
                }catch (e: Exception) {
                    _errorMessageLiveData.value = e.localizedMessage
                    e.localizedMessage?.let { Log.d("registerScreenErorr", it.toString()) }
                }

            } catch (e: Exception) {
                _errorMessageLiveData.value = e.localizedMessage
            }
        }
    }
}