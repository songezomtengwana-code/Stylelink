package com.ekasi.studios.stylelink.ui.auth.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ekasi.studios.stylelink.data.repository.AuthRepository
import com.ekasi.studios.stylelink.navigation.Screen
import com.ekasi.studios.stylelink.utils.services.popUpToTop
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val navController: NavController
) : ViewModel() {
    var isLoading by mutableStateOf(false)
    private val _userLiveData = MutableLiveData<FirebaseUser?>()
    val userLiveData: LiveData<FirebaseUser?> get() = _userLiveData

    private val _errorMessageLiveData = MutableLiveData<String?>()
    val errorMessageLiveData: LiveData<String?> get() = _errorMessageLiveData

    fun signin(email: String, password: String) {
        showLoading()
        viewModelScope.launch {
            try {
                val result = authRepository.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            dismissLoading()
                            navigateToHome()
                            Log.d(
                                "authResponse", task.result.user.toString()
                            )
                        } else {
                            dismissLoading()
                        }
                    }
                _userLiveData.value = result.result.user

            } catch (e: Exception) {
                _errorMessageLiveData.value = e.localizedMessage
            }
        }
    }

    private fun navigateToHome() {
        navController.navigate(Screen.Home.route) { popUpToTop(navController) }
    }

    fun navigateToSignup() {
        navController.navigate(Screen.Signup.route) { popUpToTop(navController) }
    }

    private fun showLoading() {
        isLoading = true
    }

    private fun dismissLoading() {
        isLoading = false
    }
}

sealed class LoginState {
    data class Error(val message: String) : LoginState()
    data object Loading : LoginState()
    data object Success : LoginState()
    data object Default : LoginState()
}