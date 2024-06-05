package com.ekasi.studios.stylelink.ui.booking

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BookingScreen(
    serviceId: String?
) {
    val id = serviceId ?: return
    Text("Booking MaNigga!!!!! $id", style = MaterialTheme.typography.headlineLarge)
}