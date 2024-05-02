package com.ekasi.studios.stylelink.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekasi.studios.stylelink.data.repository.ServicesRepository
import com.ekasi.studios.stylelink.utils.states.ServicesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServicesViewModel(
    private val repository: ServicesRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ServicesState>(ServicesState.Loading)
    val state: StateFlow<ServicesState> = _state

    init {
        viewModelScope.launch { }

        fun fetchStoreServices(storeId: String) {
            viewModelScope.launch {
                try {
                    val response = repository.fetchStoreServices(storeId)
                    _state.value = ServicesState.MultipleServicesSuccess(response)
                } catch (e: Exception) {
                    _state.value = ServicesState.Error(e.message.toString())
                }
            }
        }

        fun fetchSingleService(id: String) {
            viewModelScope.launch {
                try {
                    val response = repository.fetchServiceById(id)
                    _state.value = ServicesState.SingleServiceSuccess(response)
                } catch (e: Exception) {
                    _state.value = ServicesState.Error(e.message.toString())
                }
            }
        }

    }
}

