package com.ekasi.studios.stylelink.ui.booking.state

import com.ekasi.studios.stylelink.data.model.Marker
import com.ekasi.studios.stylelink.data.model.Service
import com.ekasi.studios.stylelink.data.model.appointment.AppointmentModel

sealed class BookingUIState {
    data object Loading : BookingUIState()
    data class Success(val service: Service) : BookingUIState()
    data class Error(val message: String) : BookingUIState()
}

sealed class BookingScheduleState {
    data object Loading : BookingScheduleState()
    data class Success(val appointment: AppointmentModel) : BookingScheduleState()
    data class Error(val message: String) : BookingScheduleState()
}

sealed class StoreLocationsState {
    data object Loading : StoreLocationsState()
    data class Success(val storeLocations: List<Marker>) : StoreLocationsState()
    data class Error(val message: String) : StoreLocationsState()
}

sealed class BookingProcessingState {
    data object Loading : BookingProcessingState()
    data class Success(val appointment: AppointmentModel) : BookingProcessingState()
    data class Error(val message: String) : BookingProcessingState()
}

sealed class BookingState {}