package com.ekasi.studios.stylelink.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationViewModel(private val application: Application) : ViewModel() {

    private val _locationState = MutableStateFlow<LocationState>(LocationState.Loading)
    val locationState: StateFlow<LocationState> = _locationState


    /**
     * Save location state
     *
     * @param coordinates contains location points in longitude and latitude
     */
    fun storeCurrentLocation(coordinates: Pair<Double, Double>) {
        viewModelScope.launch {
            try {
                _locationState.value = LocationState.Coordinates(coordinates)
                Log.d("storeCurrentLocation", "Coordinates: $coordinates")
            } catch (e: Exception) {
                Log.d("storeCurrentLocation", "ERROR: ${e.message}")
                _locationState.value = LocationState.Error(e.message.toString())
            }
        }
    }

    fun onGetCurrentLocationFailed(message: String){
        viewModelScope.launch{
            _locationState.value = LocationState.Error(message)
        }
    }
}

sealed class LocationState {
    data object Loading : LocationState()
    data object PermissionDenied : LocationState()
    data class SalonAvailable(val markers: List<Marker>) : LocationState()
    data class Coordinates(val coordinates: Pair<Double, Double>) : LocationState()
    data class Error(val message: String) : LocationState()
}

data class LocationPermissionRequest(val rationale: String = "App needs location to show maps")
