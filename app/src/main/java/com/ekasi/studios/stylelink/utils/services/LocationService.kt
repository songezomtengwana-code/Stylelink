package com.ekasi.studios.stylelink.utils.services

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ILocationService {
    fun requestLocationUpdates(): Flow<LatLng?>
    fun requestCurrentLocation(): Flow<LatLng?>
}

class LocationService @Inject constructor(
    private val application: ApplicationContext,
    private val locationClient: FusedLocationProviderClient
): ILocationService {

    @SuppressLint("MissingPermission")
//    @RequiresApi(Build.VERSION_CODES)
    override fun requestLocationUpdates(): Flow<LatLng?> {
        TODO("Not yet implemented")
    }

    override fun requestCurrentLocation(): Flow<LatLng?> {
        TODO("Not yet implemented")
    }

}