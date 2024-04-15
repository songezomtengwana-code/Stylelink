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
import kotlinx.coroutines.launch

class MainViewModel(
    private val navController: NavController,
    private val authRepository: AuthRepository,
    private val userViewModel: UserViewModel
) : ViewModel() {
    var user: List<ServerUserModel>? by mutableStateOf(null)
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
            Log.d("configuration", user.toString())
        }
    }
}