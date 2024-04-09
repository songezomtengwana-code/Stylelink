package com.ekasi.studios.stylelink.ui.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ekasi.studios.stylelink.data.model.UserRegistrationModel

class RegisterViewModel : ViewModel() {
    var fullname by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var isSnackbarActive by mutableStateOf(false)

    fun registerUserAccount(fullname: String, email: String, password: String) {

        if (fullname.isNotEmpty() || email.isNotEmpty() || password.isNotEmpty()) {
            Log.d("triggerRegisterUser", "all fields are filled")
            isLoading = true
            // initiating server sign in
        } else {
            Log.d("triggerRegisterUser", "one of the fields is not field")
        }
    }
}