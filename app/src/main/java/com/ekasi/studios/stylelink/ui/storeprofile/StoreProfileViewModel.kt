package com.ekasi.studios.stylelink.ui.storeprofile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ekasi.studios.stylelink.data.repository.StoreRepository
import com.ekasi.studios.stylelink.utils.services.stores.models.Store
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StoreProfileViewModel(
    private val storeRepository: StoreRepository,
    private val navController: NavController
) : ViewModel() {
    private var _state = MutableStateFlow<StoreProfileState>(StoreProfileState.Loading)

    /**
     * @param storeRepository
     */
    val state: MutableStateFlow<StoreProfileState> = _state

    /**
     * @throws IllegalStateException
     */
    init {
        viewModelScope.launch {
            Log.d("StoreProfileViewModel", "Store Profile View Model Actively Loaded")
        }
    }

    fun fetchStoreProfile(storeId: String) {
        viewModelScope.launch {
            try {
                val result = storeRepository.fetchStoreProfile(storeId)
                _state.value = StoreProfileState.Success(store = result)
                _state.value = StoreProfileState.Success(store = result)
                Log.d("fetchStoreProfile", "returnedStore: ${_state.value}")
            } catch (e: Exception) {
                _state.value = StoreProfileState.Error(e.message.toString())
                Log.d("fetchStoreProfile", "nullPointerException: ${e.message}")
            }
        }
    }

    fun refreshData(storeId: String) {
        viewModelScope.launch {
            _state.value = StoreProfileState.Loading
            fetchStoreProfile(storeId)
        }
    }

    fun clearState() {
       viewModelScope.launch {
           try {
               navController.popBackStack()
               _state.value = StoreProfileState.Loading
           } catch (e: Exception) {
               Log.d("clearState", e.message.toString())
           }
       }
    }

}

sealed class StoreProfileState {
    data object Loading : StoreProfileState()
    data class Success(val store: Store) : StoreProfileState()
    data class Error(val message: String) : StoreProfileState()
}