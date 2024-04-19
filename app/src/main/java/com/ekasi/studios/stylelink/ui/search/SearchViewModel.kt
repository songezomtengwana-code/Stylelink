package com.ekasi.studios.stylelink.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SearchViewModel(): ViewModel() {
    var isLoading by mutableStateOf(false)

}