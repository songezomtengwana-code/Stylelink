package com.ekasi.studios.stylelink.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekasi.studios.stylelink.data.model.Marker
import com.ekasi.studios.stylelink.data.repository.MarkersRepository
import com.google.android.libraries.places.api.Places
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PlacesViewModel(
    private val application: Application,
    private val markersRepository: MarkersRepository
) : ViewModel() {
    private var _placesState = MutableStateFlow<PlacesState>(PlacesState.Loading)
    val placesState: MutableStateFlow<PlacesState> = _placesState


    init {
        viewModelScope.launch {
            Places.initialize(
                application.applicationContext,
                "AIzaSyD8ntCYKKgjYq1LIZ3Udm_GGqsY9dpWAW0"
            )
        }
    }

    @Suppress("DEPRECATION")
//    fun fetchMapPlaces() {
//        val placesClient: PlacesClient = Places.createClient(application.applicationContext)
//
//        val placeFields = listOf(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG,)
//        val request = FindCurrentPlaceRequest.newInstance(placeFields)
//        val beautySalons = mutableStateOf<List<Place>>(emptyList())
//        if (ActivityCompat.checkSelfPermission(
//                application.applicationContext,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                application.applicationContext,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            _placesState.value =
//                PlacesState.Error(message = "Location permission have been revoked, please allow access")
//            return
//        } else {
//            placesClient.findCurrentPlace(request).addOnSuccessListener { response ->
//                val places = response.placeLikelihoods
////                    .filter { it.place.types!!.contains(Place.Type.BEAUTY_SALON) }
//                    .MapScreen { it.place }
//
//                _placesState.value = PlacesState.Success(places)
//                Log.d("fetchPlaces","places: ${places.size}")
//            }.addOnFailureListener { exception ->
//                // Handle failure
//                Log.d("findPlaces", exception.message.toString())
//                _placesState.value = PlacesState.Error(message = exception.message.toString())
//            }
//        }
//    }

    fun fetchStoreMarkers() {
        viewModelScope.launch {
            try {
                val response = markersRepository.fetchMarkers()
                _placesState.value = PlacesState.Success(response)
            } catch (e: Error) {
                _placesState.value = PlacesState.Error(e.message.toString())
            }
        }
    }
}

sealed class PlacesState {
    data object Loading : PlacesState()
    data class Success(val places: List<Marker>) : PlacesState()

    data class Error(val message: String) : PlacesState()
}