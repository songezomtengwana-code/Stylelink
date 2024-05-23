package com.ekasi.studios.stylelink.utils.services.markers

import com.ekasi.studios.stylelink.data.model.Marker
import retrofit2.http.GET
import retrofit2.http.Path

private const val GET_MARKER_BY_STORE_ID = "api/markers/store/{storeId}"
private const val GET_ALL_MARKERS = "api/markers"
private const val GET_MARKERS_BY_ID = "api/markers/{id}"

interface MarkerService {

    @GET(GET_MARKER_BY_STORE_ID)
    suspend fun getMarkersByStoreId(@Path("storeId") storeId: String): List<Marker>

    @GET(GET_ALL_MARKERS)
    suspend fun getAllMarkers(): List<Marker>

    @GET(GET_MARKERS_BY_ID)
    suspend fun getMarkerById(@Path("id") id: String): Marker
}