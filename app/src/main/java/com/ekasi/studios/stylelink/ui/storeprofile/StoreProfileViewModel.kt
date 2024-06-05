package com.ekasi.studios.stylelink.ui.storeprofile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ekasi.studios.stylelink.data.repository.ProductRepository
import com.ekasi.studios.stylelink.data.repository.ServicesRepository
import com.ekasi.studios.stylelink.data.repository.StoreRepository
import com.ekasi.studios.stylelink.navigation.Screen
import com.ekasi.studios.stylelink.utils.services.stores.models.Store
import com.ekasi.studios.stylelink.utils.states.ProductState
import com.ekasi.studios.stylelink.utils.states.ServicesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StoreProfileViewModel(
    private val storeRepository: StoreRepository,
    private val servicesRepository: ServicesRepository,
    private val productRepository: ProductRepository,
    private val navController: NavController
) : ViewModel() {
    private var _state = MutableStateFlow<StoreProfileState>(StoreProfileState.Loading)
    private var _productState = MutableStateFlow<ProductState>(ProductState.Loading)
    private var _servicesState = MutableStateFlow<ServicesState>(ServicesState.Loading)

    /**
     * @param storeRepository
     */
    val state: MutableStateFlow<StoreProfileState> = _state
    val servicesState: StateFlow<ServicesState> = _servicesState
    val productState: StateFlow<ProductState> = _productState

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
                _servicesState.value = ServicesState.Loading
            } catch (e: Exception) {
                Log.d("clearState", e.message.toString())
            }
        }
    }

    fun fetchStoreServices(storeId: String) {
        viewModelScope.launch {
            try {
                val response = servicesRepository.fetchStoreServices(storeId)
                _servicesState.value = ServicesState.MultipleServicesSuccess(response)
                Log.d("fetchStoreServices", "returnedServices: ${_servicesState.value}")
            } catch (e: Exception) {
                _servicesState.value = ServicesState.Error(e.message.toString())
                Log.d("fetchStoreServices", "error: ${_servicesState.value}")
            }
        }
    }

    fun fetchSingleService(id: String) {
        viewModelScope.launch {
            try {
                val response = servicesRepository.fetchServiceById(id)
                _servicesState.value = ServicesState.SingleServiceSuccess(response)
            } catch (e: Exception) {
                _servicesState.value = ServicesState.Error(e.message.toString())
            }
        }
    }

    suspend fun fetchProductsByStoreId(id: String) {
        viewModelScope.launch {
            try {
                val response = productRepository.fetchProductsByStoreId(id)
                _productState.value = ProductState.MultipleProductsSuccess(response)
                Log.d("fetchStoreProducts", "returnedProducts: ${_productState.value}")

            } catch (e: Exception) {
                _productState.value = ProductState.Error(e.message.toString())
                Log.d("fetchStoreProducts", "error: ${_productState.value}")

            }
        }
    }

     fun backNavigation() {
        viewModelScope.launch {
            navController.popBackStack()
        }
    }

    fun startBooking(serviceId: String) {
        viewModelScope.launch {
            navController.navigate(Screen.Booking.route.replace("{serviceId}", serviceId))
        }
    }
}

sealed class StoreProfileState {
    data object Loading : StoreProfileState()
    data class Success(val store: Store) : StoreProfileState()
    data class Error(val message: String) : StoreProfileState()
}