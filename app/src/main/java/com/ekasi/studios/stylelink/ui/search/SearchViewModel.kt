package com.ekasi.studios.stylelink.ui.search

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class SearchViewModel(
    private val navController: NavController,
    private val application: Application
) : ViewModel() {
    var isLoading by mutableStateOf(false)

    fun navigateTo(route: String) {
        navController.navigate(route)
    }
}