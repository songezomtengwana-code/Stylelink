package com.ekasi.studios.stylelink.ui.search

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ekasi.studios.stylelink.data.repository.MarkersRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class SearchViewModel(
    private val navController: NavController,
    private val application: Application,
    private val markersRepository: MarkersRepository
) : ViewModel() {
    var isLoading by mutableStateOf(false)
    private var _activeMarker = mutableStateOf<LatLng>(LatLng(0.0, 0.0))
    val lastLocation: LatLng get() = _activeMarker.value

    fun navigateTo(route: String) {
        navController.navigate(route)
    }

    fun updateLastLocation(location: Pair<Double,Double>) {
        viewModelScope.launch {
            try {
                _activeMarker.value = LatLng(location.first, location.second)
            } catch (e: Exception) {
                Log.d("configureLastLocation", e.message.toString())
            }
        }
    }
}