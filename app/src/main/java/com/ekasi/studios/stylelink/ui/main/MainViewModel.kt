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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val navController: NavController,
    private val authRepository: AuthRepository,
    private val userViewModel: UserViewModel
) : ViewModel() {
    /*
     * Instantiate Default state to loading whilst awaiting server response
     */
    private var _uiState = MutableStateFlow<MainState>(MainState.Loading)
    // store the state as a val to make it accessible outside of this scope
    val uiState : StateFlow<MainState> = _uiState

    // user information container
    var user: ServerUserModel? by mutableStateOf(null)
    var protoUserDetailsUserId: String? by mutableStateOf("")

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

    fun navigateTo(route: String) {
        navController.navigate(route)
    }


    fun configuration() {
        viewModelScope.launch {
            val firebaseUser = userViewModel.getAuthUser()
            Log.d("protoDatastoreUser", user.toString())
            Log.d("getAuthUser", firebaseUser!!.email!!)
            TODO("Remove - the code block is unused")
        }
    }

    fun setUserDetails(userid: String) {
        viewModelScope.launch {
            userViewModel.setUserDetails(userid)
        }
    }

    fun displayLoadingState() {
        viewModelScope.launch {
            _uiState.value = MainState.Loading
        }
    }

    fun fetchUser() {
        viewModelScope.launch {
            try {
                Log.d("fetchUser", "fetching $protoUserDetailsUserId server information")
                if (protoUserDetailsUserId!!.isNotEmpty()) {
                    val details = userViewModel.fetchUser(protoUserDetailsUserId!!)
                    // change state after successful data retrieval
                    _uiState.value = MainState.Success(details)
                    user = details
                    Log.d("fetchUser", "results: $user")
                } else {
                    Log.d("fetchUser", "userid: $protoUserDetailsUserId")
                    _uiState.value = MainState.Error("Unable to connect to server, please refresh.")
                }
            } catch (e: Exception) {
                _uiState.value = MainState.Error(e.message.toString())
                Log.d("fetchUser", "nullPointerException: ${e.message.toString()}")
            }
        }
    }
}

sealed class MainState {
    object Loading: MainState()
    data class Success(val user: ServerUserModel): MainState()
    data class Error(val message: String): MainState()
}