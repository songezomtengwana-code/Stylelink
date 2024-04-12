package com.ekasi.studios.stylelink.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekasi.studios.stylelink.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _userLiveData = MutableLiveData<FirebaseUser?>()
    val userLiveData: LiveData<FirebaseUser?> get() = _userLiveData

    private val _errorMessageLiveData = MutableLiveData<String?>()
    val errorMessageLiveData: LiveData<String?> get() = _errorMessageLiveData

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = repository.signInWithEmailAndPassword(email, password)
                _userLiveData.value = result.result.user

            } catch (e: Exception) {
                _errorMessageLiveData.value = e.localizedMessage
            }
        }
    }
}