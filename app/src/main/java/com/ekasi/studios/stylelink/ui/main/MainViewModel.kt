package com.ekasi.studios.stylelink.ui.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ekasi.studios.stylelink.data.repository.AuthRepository
import com.ekasi.studios.stylelink.navigation.Screen
import com.ekasi.studios.stylelink.utils.services.popUpToTop
import com.ekasi.studios.stylelink.viewModels.UserViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val navController: NavController,
    private val authRepository: AuthRepository,
    private val userViewModel: UserViewModel
) : ViewModel() {
    var user: String? by mutableStateOf(null)
    var protoUserDetailsUserId: String? by mutableStateOf("empty")

    init {
        viewModelScope.launch {
            protoUserDetailsUserId = userViewModel.getUserDetails()
                .collect { formattedValue -> protoUserDetailsUserId = formattedValue }.toString()
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            navController.navigate(Screen.Login.route) {
                popUpToTop(navController)
            }
        }
    }

    fun configuration() {
        viewModelScope.launch {
            val firebaseUser = userViewModel.getAuthUser()
            Log.d("protoDatastoreUser", user.toString())
            Log.d("getAuthUser", firebaseUser!!.email!!)
        }
    }

    fun setUserDetails(userid: String) {
        viewModelScope.launch {
            userViewModel.setUserDetails(userid)
        }
    }

    fun getUserDetails() {
        var container: String by mutableStateOf("")
        viewModelScope.launch {
            try {
                Log.d("getUserDetails", "initiating")
                val details = userViewModel.getUserDetails().collect { format ->
                    container = format
                }
                protoUserDetailsUserId = "response"
                Log.d("getUserDetails", "results: $details")
            } catch (e: Exception) {
                Log.d("getUserDetails", "nullPointerException: ${e.message.toString()}")
            }
        }
    }
}