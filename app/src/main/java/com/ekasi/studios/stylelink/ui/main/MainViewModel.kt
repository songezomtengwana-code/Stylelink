package com.ekasi.studios.stylelink.ui.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ekasi.studios.stylelink.data.model.ServerUserModel
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
    var user: ServerUserModel? by mutableStateOf(null)
    var protoUserDetailsUserId: String? by mutableStateOf("")
    var success: Boolean by mutableStateOf(false)
    var isLoading by mutableStateOf(true);

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

    fun fetchUser() {
        isLoading = true
        viewModelScope.launch {
            try {
                Log.d("fetchUser", "initiating")
                if (protoUserDetailsUserId!!.isNotEmpty()) {
                    val details = userViewModel.fetchUser(protoUserDetailsUserId!!)
                    user = details
                    Log.d("fetchUser", "results: $user")
                    isLoading = false
                    success = true
                } else {
                    Log.d("fetchUser", "userid: $protoUserDetailsUserId")
                    isLoading = false
                    success = false
                }
            } catch (e: Exception) {
                isLoading = false
                success = false
                Log.d("fetchUser", "nullPointerException: ${e.message.toString()}")
            }
        }
    }
}