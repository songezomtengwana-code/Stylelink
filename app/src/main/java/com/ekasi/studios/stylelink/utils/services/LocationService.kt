package com.ekasi.studios.stylelink.utils.services

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
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
    private val application: Application,
    private val context: Context,
    private val locationClient: FusedLocationProviderClient
): ILocationService {
//    val test = ContextCompat.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION === PackageManager.PERMISSION_GRANTED)

    @SuppressLint("MissingPermission")
//    @RequiresApi(Build.VERSION_CODES)
    override fun requestLocationUpdates(): Flow<LatLng?> {
        TODO("Not yet implemented")

    }

    override fun requestCurrentLocation(): Flow<LatLng?> {
        TODO("Not yet implemented")
    }

}