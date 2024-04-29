package com.ekasi.studios.stylelink.ui.storeprofile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekasi.studios.stylelink.data.repository.StoreRepository
import com.ekasi.studios.stylelink.utils.services.stores.models.Store
import kotlinx.coroutines.launch

class StoreProfileViewModel(
    private val storeRepository: StoreRepository
) : ViewModel() {

    /**
     * @throws IllegalStateException
     */
    init {
        viewModelScope.launch {
            Log.d("StoreProfileViewModel", "Store Profile View Model Actively Loaded")
        }
    }

}

sealed class StoreProfileState {
    data object Loading : StoreProfileState()
    data class Success(val store: Store) : StoreProfileState()
    data class Error(val message: String) : StoreProfileState()
}