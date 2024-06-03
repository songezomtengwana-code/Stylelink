package com.ekasi.studios.stylelink.base.components.StorePreview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekasi.studios.stylelink.data.repository.StoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StorePreviewViewModel(
    private val storeRepository: StoreRepository
): ViewModel() {
    private var _state = MutableStateFlow<StorePreviewState>(StorePreviewState.Loading)
    val state: MutableStateFlow<StorePreviewState> = _state

    fun fetchStoreProfile(storeId: String) {
        viewModelScope.launch {
            try {
                _state.value = StorePreviewState.Loading
                val result = storeRepository.fetchStoreProfile(storeId)
                _state.value = StorePreviewState.Success(store = result)
                _state.value = StorePreviewState.Success(store = result)
                Log.d("fetchStoreProfile", "returnedStore: ${_state.value}")
            } catch (e: Exception) {
                _state.value = StorePreviewState.Error(e.message.toString())
                Log.d("fetchStoreProfile", "nullPointerException: ${e.message}")
            }
        }
    }

}

