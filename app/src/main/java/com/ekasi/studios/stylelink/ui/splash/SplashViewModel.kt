package com.ekasi.studios.stylelink.ui.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ekasi.studios.stylelink.data.repository.AuthRepository
import com.ekasi.studios.stylelink.navigation.Screen
import com.ekasi.studios.stylelink.utils.services.popUpToTop
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authRepository: AuthRepository,
    private val navController: NavController,
) : ViewModel() {
    var isLoading by mutableStateOf(false)

    fun checkForUserActivity() {
        viewModelScope.launch {
            var currentUser = authRepository.getCurrentUser()
            navigateTo(Screen.Home.route)
        }
    }

    fun navigateTo(route: String) {
        navController.navigate(route) { popUpToTop(navController) }
    }
}