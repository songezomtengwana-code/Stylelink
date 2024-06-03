package com.ekasi.studios.stylelink.ui.screens.home

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

class HomeViewModel(
    private val navController: NavController,
    private val authRepository: AuthRepository,
    private val userViewModel: UserViewModel
) : ViewModel() {
    val user1 = ServerUserModel(
        userId = "fgy893fgu3gb7438gb",
        id = 1, // Auto-generated, can be any int
        _id = "shrt_u89ser_id", // Replace with a unique user ID string
        address = "123 Main St",
        city = "New York",
        country = "USA",
        dateOfBirth = "1990-01-01",
        email = "jane.doe@example.com",
        favoriteHairstylist = "John Smith",
        favorites = listOf("hairstyle1", "hairstyle2"),
        following = listOf("stylist1", "stylist2"),
        fullname = "Jane Doe",
        hairColor = "Blonde",
        hairType = "Straight",
        latitude = "40.7128", // Replace with actual latitude
        longitude = "-74.0059", // Replace with actual longitude
        password = "hashed_password", // Replace with a hashed password
        phoneNumber = "+1234567890",
        preferredSalon = "Salon XYZ",
        profileImageURL = "https://example.com/profile_pic.jpg",
        registrationDate = "2024-06-02",
        state = "NY",
        zipCode = "10001"
    )

    /*
     * Instantiate Default state to loading whilst awaiting server response
     */
    private var _uiState = MutableStateFlow<MainState>(MainState.Success(user1))

    // store the state as a val to make it accessible outside of this scope
    val uiState: StateFlow<MainState> = _uiState

    // user information container
    var user: ServerUserModel? by mutableStateOf(null)
    var protoUserDetailsUserId: String? by mutableStateOf("")
    var token: String? by mutableStateOf("")

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

    fun setUserDetails(userid: String, token: String) {
        viewModelScope.launch {
            userViewModel.setUserDetails(userid, token)
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
                Log.d("fetchUser", "fetching $protoUserDetailsUserId")

                //  fetch access token from storage
                token = userViewModel.retrieveToken()
                Log.d("fetchUser", "token: $token")

                if (protoUserDetailsUserId!!.isNotEmpty()) {
                    val details = userViewModel.fetchUser(protoUserDetailsUserId!!)
                    // change state after successful data retrieval
                    _uiState.value = MainState.Success(details)
                    user = details
                    Log.d("fetchUser", "results: $details")
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

    fun storeToken(token: String) {
        viewModelScope.launch {
            userViewModel.storeToken(token)
        }
    }
}

sealed class MainState {
    object Loading : MainState()
    data class Success(val user: ServerUserModel) : MainState()
    data class Error(val message: String) : MainState()
}