package com.ekasi.studios.stylelink.ui.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
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
    var user: String? by mutableStateOf(null)
    var protoUserDetailsUserId: String by mutableStateOf("empty")

    init {
        viewModelScope.launch {
            Log.v("MainViewModel init{}", "Initiating initEffect")
            protoUserDetailsUserId = userViewModel.getUserDetails()
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
            user = userViewModel.getUserDetails()
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
            Log.v("getUserDetails", "initiating")
                val details = userViewModel.getUserDetails()

                protoUserDetailsUserId = details.toString()

                Log.v(
                    "getUserDetails",
                    "userid: $details"
                )
//            } catch (e: Exception) {
//                Log.v("getUserDetails", e.message.toString())
//            }
        }
    }
}