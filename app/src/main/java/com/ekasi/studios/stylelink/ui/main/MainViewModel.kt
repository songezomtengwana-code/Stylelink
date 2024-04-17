package com.ekasi.studios.stylelink.ui.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ekasi.studios.stylelink.data.model.ServerUserModel
import com.ekasi.studios.stylelink.data.repository.AuthRepository
import com.ekasi.studios.stylelink.navigation.Screen
import com.ekasi.studios.stylelink.utils.services.popUpToTop
import com.ekasi.studios.stylelink.viewModels.UserViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val navController: NavController,
    private val authRepository: AuthRepository,
    private val userViewModel: UserViewModel
) : ViewModel() {
    var user: List<ServerUserModel>? by mutableStateOf(null)
    var protoUserDetails: String = "empty"
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
            user = userViewModel.getCurrentUser()
            val firebaseUser = userViewModel.getAuthUser()
            Log.d("configuration", user.toString())
            Log.d("getAuthUser", firebaseUser!!.email!!)
        }
    }

    fun setUserDetails(userid: String) {
        viewModelScope.launch {
            userViewModel.setUserDetails(userid)
//            protoUserDetails = userid
        }
    }

    fun getUserDetails() {
        viewModelScope.launch {
            val details = userViewModel.getUserDetails()!!.collect { protoUserDetails ->
                protoUserDetails.userId
            }

            protoUserDetails = details.toString()

            Log.v(
                "getUserDetails",
                details.toString()
            )
        }
    }
}