package com.ekasi.studios.stylelink.ui.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekasi.studios.stylelink.data.repository.ServicesRepository
import com.ekasi.studios.stylelink.ui.booking.services.BookingRepository
import com.ekasi.studios.stylelink.ui.booking.state.BookingProcessingState
import com.ekasi.studios.stylelink.ui.booking.state.BookingScheduleState
import com.ekasi.studios.stylelink.ui.booking.state.BookingUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookingViewModel(
    private val bookingRepository: BookingRepository,
    private val servicesRepository: ServicesRepository
) : ViewModel() {
    private var _bookingUiState = MutableStateFlow<BookingUIState>(BookingUIState.Loading)
    private var _bookingScheduleState = MutableStateFlow<BookingScheduleState>(BookingScheduleState.Loading)
    private var _bookingProcessState = MutableStateFlow<BookingProcessingState>(BookingProcessingState.Loading)

    val uiState: StateFlow<BookingUIState> = _bookingUiState
    val scheduleState: StateFlow<BookingScheduleState> = _bookingScheduleState
    val processState: StateFlow<BookingProcessingState> = _bookingProcessState

    fun fetchService(id: String) {
        viewModelScope.launch {
            try {
                val response = servicesRepository.fetchServiceById(id)
                _bookingUiState.value = BookingUIState.Success(response)
            } catch (e: Exception) {
                _bookingUiState.value = BookingUIState.Error(e.message ?: "An error occurred")
            }
        }
    }
}