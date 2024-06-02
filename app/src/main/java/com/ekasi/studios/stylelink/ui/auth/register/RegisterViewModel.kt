package com.ekasi.studios.stylelink.ui.auth.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ekasi.studios.stylelink.data.model.RegistrationUserModel
import com.ekasi.studios.stylelink.data.repository.AuthRepository
import com.ekasi.studios.stylelink.data.repository.UserRepository
import com.ekasi.studios.stylelink.navigation.Screen

import com.ekasi.studios.stylelink.utils.services.popUpToTop
import com.ekasi.studios.stylelink.viewModels.UserViewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val userViewModel: UserViewModel,
    private val navController: NavController,
    private val authRepository: AuthRepository
) : ViewModel() {
    var isLoading: Boolean by mutableStateOf(false)
    var isSnackBarVisible by mutableStateOf(false)
    var firebaseUser: FirebaseUser? by mutableStateOf(null)

    init {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()

            if (currentUser !== null) {
                firebaseUser = currentUser
            }
        }
    }

    fun registerUserAccount(
        fullname: String,
        email: String,
        password: String,
        phoneNumber: String,
        address: String,
        city: String,
        state: String
    ) {
        var value by mutableStateOf(false)
        showLoading()
        Log.d("registerUserAccount", "Starting Registration Service")

        val newUserAccount = RegistrationUserModel(
            fullname = fullname,
            email = authRepository.getCurrentUser()?.email!!,
            password = password,
            phoneNumber = phoneNumber,
            address = address,
            city = city,
            state = state
        )
        Log.d("registerUserAccount", newUserAccount.toString())
        viewModelScope.launch {
            try {
                Log.d("registerUserAccount", "Initiating Registration Service")
                val response = userRepository.createUserAccount(newUserAccount)
                Log.d("registerUserAccount", "token : ${response}")

                if (response.success) {
                    userViewModel.setUserDetails(response.result, response.token)
//                    userViewModel.storeToken(response.token)
                    Log.d("registerUserAccount", "onSuccess: ${response.message}")
                    navigateTo(Screen.Home.route)
                } else {
                    Log.d("registerUserAccount", response.message)
                }
            } catch (e: Exception) {
                Log.d("registerUserAccount", e.toString())
                activateSnackBar("Sorry, there was a problem with creating your account")
                dismissLoading()
            }

        }

        if (value) {
            dismissLoading()
            navigateTo(Screen.Home.route)
        } else {
            showLoading()
        }
    }

    fun navigateTo(route: String) {
        navController.navigate(route) { popUpToTop(navController) }
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

    fun userEmailAddress() {
        val currentUser = authRepository.getCurrentUser()

        if (currentUser !== null) {

        }
    }
}