package com.ekasi.studios.stylelink.data.repository

import com.ekasi.studios.stylelink.data.model.Marker
import com.ekasi.studios.stylelink.utils.services.markers.MarkerService

class MarkersRepository(
    private val markersService: MarkerService
){

    suspend fun fetchMarkers(): List<Marker> {
        return markersService.getAllMarkers()
    }

    suspend fun  fetchMarkersByStoreId(storeId: String): List<Marker> {
        return markersService.getMarkersByStoreId(storeId)
    }

    suspend fun fetchMarkerById(id: String): Marker {
        return markersService.getMarkerById(id)
    }
}