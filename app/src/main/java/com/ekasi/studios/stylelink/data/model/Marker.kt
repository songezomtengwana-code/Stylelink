package com.ekasi.studios.stylelink.data.model

data class Marker(
    val id: String,
    val storeId: String,
    val coordinates: Coordinates,
    val previewImage: String,
    val markerAddress: String,
    val storeName: String
)

data class Coordinates(val latitude: Double, val longitude: Double)