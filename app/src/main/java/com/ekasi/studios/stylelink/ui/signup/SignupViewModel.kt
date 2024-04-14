package com.ekasi.studios.stylelink.ui.signup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ekasi.studios.stylelink.data.repository.AuthRepository
import com.ekasi.studios.stylelink.navigation.Screen
import com.ekasi.studios.stylelink.utils.services.popUpToTop
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class SignupViewModel(
    private val authRepository: AuthRepository,
    private val navController: NavHostController
) : ViewModel() {
    var isLoading by mutableStateOf(false)

    // authenticated user containers
    private val _userLiveData = MutableLiveData<FirebaseUser?>()
    private val _errorMessageLiveData = MutableLiveData<String?>()
    val userLiveData: LiveData<FirebaseUser?> get() = _userLiveData
    val errorMessageLiveData: LiveData<String?> get() = _errorMessageLiveData

    fun authenticateUserAccount(email: String, password: String) {
        if (email.isNotEmpty() || password.isNotEmpty()) {
            Log.d("triggerRegisterUser", "all fields are filled")
            showLoading()
            // initiating server sign in

            viewModelScope.launch {
                try {
                    authRepository.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                dismissLoading()
                                navigateToCreateAccount()
                                Log.d(
                                    "authResponse", task.result.user.toString()
                                )
                            } else {
                                dismissLoading()
                            }
                        }
                } catch (e: Exception) {
                    _errorMessageLiveData.value = e.localizedMessage
                    e.localizedMessage?.let { Log.d("authResponse", it.toString()) }
                    dismissLoading()
                }
            }
        } else {
            Log.d("authResponse", "one of the fields is not field")
            dismissLoading()
        }
    }

    private fun navigateToCreateAccount() {
        navController.navigate(Screen.Register.route)
    }

    fun navigateToLogin() {
        navController.navigate(Screen.Login.route) { popUpToTop(navController)}
    }

    private fun showLoading() {
        isLoading = true
    }

    private fun dismissLoading() {
        isLoading = false
    }
}