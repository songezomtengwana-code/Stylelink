package com.ekasi.studios.stylelink.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekasi.studios.stylelink.data.repository.StoreRepository
import com.ekasi.studios.stylelink.utils.services.stores.models.Store
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StoresViewModel(
    private val storeRepository: StoreRepository
) : ViewModel() {
    private val _storeState = MutableStateFlow<StoreState>(StoreState.Loading)

    /**
     * returns a live instance of the StoreViewModel state
     *
     * @return StoreState with one of the three properties Loading, Success or Error
     * @author @songezomtengwana-code
     */
    val storeState: StateFlow<StoreState> = _storeState

    init {
        viewModelScope.launch {
            initFetchAllStore()
        }
    }


    private suspend fun initFetchAllStore() {
        viewModelScope.launch {
            try {
                val response = storeRepository.fetchStores()
                _storeState.value = StoreState.Success(stores = response)
                Log.d("initFetchStores", "response: ${response[0]}")

            } catch (e: Exception) {
                _storeState.value = StoreState.Error(message = e.message.toString() )
                Log.d("initFetchStores", "nullPointerException: ${e.message}")
            }
        }
    }
}

sealed class StoreState {
    data object Loading : StoreState()
    data class Success(val stores: List<Store>) : StoreState()
    data class Error(val message: String) : StoreState()
}