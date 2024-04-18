package com.ekasi.studios.stylelink.ui.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.ekasi.studios.stylelink.utils.services.popUpToTop
import com.ekasi.studios.stylelink.viewModels.UserViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import retrofit2.Call

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val userViewModel: UserViewModel,
    private val navController: NavController
) : ViewModel() {
    var isLoading: Boolean by mutableStateOf(false)
    var isSnackBarVisible by mutableStateOf(false)
    private val _userLiveData = MutableLiveData<FirebaseUser?>()
    private val _userServerData = MutableLiveData<ServerUserModel?>()
    val userLiveData: LiveData<FirebaseUser?> get() = _userLiveData
    val userServerLiveData: LiveData<ServerUserModel?> get() = _userServerData

    private val _errorMessageLiveData = MutableLiveData<String?>()
    val errorMessageLiveData: LiveData<String?> get() = _errorMessageLiveData

    fun registerUserAccount(
        fullname: String,
        email: String,
        password: String,
        phoneNumber: String
    ) {
        var value by mutableStateOf(false)
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
                Log.d("registerUserAccount", "response : ${response.result}")

                if (response.success) {
                    userViewModel.setUserDetails(response.result._id)
                    Log.d("registerUserAccount", "onSuccess: ${response.message}")
                    navigateTo(Screen.Main.route)
                } else {
                    Log.d("registerUserAccount", response.message)
                }
            } catch (e: Exception) {
                Log.d("registerUserAccount", e.message.toString())
                activateSnackBar("Sorry, there was a problem with creating your account")
                dismissLoading()
            }
//            try {
//
//                val profileConfiguration = userProfileChangeRequest {
//                    displayName = fullname
//                }
//                val user = Firebase.auth.currentUser
//
//                user!!.updateProfile(profileConfiguration)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            _userServerData.value = response.result
//
//                            try {
//                                dismissLoading()
//                                navigateTo(Screen.Main.route)
//                                Log.d("registerUserAccount", response.toString())
//
//                            } catch (e: Exception) {
//                                Log.d(
//                                    "registerUserAccount",
//                                    "navigationException: ${e.message.toString()}"
//                                )
//                            }
//                        } else {
//                            Log.d(
//                                "registerUserAccount",
//                                task.exception.toString()
//                            )
//                        }
//                    }
//
//
//            } catch (e: Exception) {
//                _errorMessageLiveData.value = e.localizedMessage
//                activateSnackBar("Sorry, there was a problem with creating your account")
//                dismissLoading()
//            }
        }

        if (value) {
            dismissLoading()
            navigateTo(Screen.Main.route)
        } else {
            showLoading()
        }
    }

    private fun navigateTo(route: String) {
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
}