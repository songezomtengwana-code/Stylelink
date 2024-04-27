import android.app.Application
import android.location.Location
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocationViewModel(private val application: Application) : ViewModel() {
//
//    private val _locationState = MutableStateFlow<LocationState>(LocationState.Loading)
//    val locationState: StateFlow<LocationState> = _locationState.asStateFlow()
//
//    private val permissionLauncher = launcher<LocationPermissionRequest> { request, result ->
//        val granted = result.getResult() == ActivityResult.Result.Granted
//        _locationState.value = if (granted) LocationState.RequestingLocation else LocationState.PermissionDenied
//        if (granted) {
//            // Get user location here (see step 2)
//        }
//    }
//
//    fun requestLocationPermission() {
//        permissionLauncher.launch(LocationPermissionRequest())
//    }
//
//    private fun updateLocation(location: Location) {
//        _locationState.value = LocationState.LocationAvailable(location)
//    }
//
//    // Function to get location (implementation in step 2)
//    private fun getLocation() {
//        // ... (location retrieval logic)
//    }
}

sealed class LocationState {
    object Loading : LocationState()
    object RequestingLocation : LocationState()
    object PermissionDenied : LocationState()
    data class LocationAvailable(val location: Location) : LocationState()
}

data class LocationPermissionRequest(val rationale: String = "App needs location to show maps")
