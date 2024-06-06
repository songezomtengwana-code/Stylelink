package com.ekasi.studios.stylelink.ui.booking

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun BookingScreen(
    serviceId: String?,
    viewModel: BookingViewModel
) {
    val id = serviceId ?: return
    Text("Booking MaNigga!!!!! $id", style = MaterialTheme.typography.headlineLarge)

    LaunchedEffect(key1 = id) {
        if (id !== null) {
            viewModel.fetchService(id)
        } else {
        }
    }
}