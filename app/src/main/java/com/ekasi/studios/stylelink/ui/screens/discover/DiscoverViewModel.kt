package com.ekasi.studios.stylelink.ui.screens.discover

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ekasi.studios.stylelink.data.model.Marker
import com.ekasi.studios.stylelink.data.repository.MarkersRepository
import com.ekasi.studios.stylelink.navigation.Screen
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class DiscoverViewModel(
    private val navController: NavController,
    private val application: Application,
    private val markersRepository: MarkersRepository
) : ViewModel() {
    var isLoading by mutableStateOf(false)
    private val initialLocation = LatLng(-29.000000, 24.000000)
    private var _activeLocation = mutableStateOf<LatLng>(initialLocation)
    private var _marker = mutableStateOf<Marker?>(null)
    private var _activeStoreId = mutableStateOf("")
    private var _showModal = mutableStateOf(false)

    val lastLocation: MutableState<LatLng> = _activeLocation
    val showModalState: MutableState<Boolean> = _showModal
    val activeStoreId: MutableState<String> = _activeStoreId
    val marker: MutableState<Marker?> = _marker
    fun navigateTo(route: String) {
        navController.navigate(route)
    }

    fun updateLastLocation(location: Pair<Double, Double>) {
        viewModelScope.launch {
            try {
                _activeLocation.value = LatLng(location.first, location.second)
            } catch (e: Exception) {
                Log.d("configureLastLocation", e.message.toString())
            }
        }
    }

    fun onMarkerClick(marker: Marker) {
        viewModelScope.launch {
            try {
                Log.d("fetchStoreProfile", marker.storeId)
                _activeStoreId.value = marker.storeId
                _marker.value = marker
                _activeLocation.value = LatLng(marker.coordinates.latitude, marker.coordinates.longitude)
                _showModal.value = true
            } catch (e: Exception) {
                Log.d("onMarkerClick", e.message.toString())
            }
        }
    }

    fun back() {
        navController.popBackStack()
    }

    fun bookServicesHandler() {
        viewModelScope.launch {
            val storeProfileRoute =
                Screen.StoreProfile.route.replace("{storeId}", activeStoreId.value)
            navController.navigate(storeProfileRoute)
        }
    }
}