package com.ekasi.studios.stylelink.data.model.appointment

data class AppointmentModel(
    val ref: String,
    val userId: String, // Assuming ObjectId is represented as String here
    val storeId: String,
    val markerId: String?, // Optional since markerId might be null
    val serviceId: String,
    val date: Long, // Convert to milliseconds before passing
    val time: String,
    val duration: Int,
    val expiresAt: Long, // Convert to milliseconds before passing
    val status: AppointmentStatus,
    val paymentVerified: Boolean,
    val notes: String?
)

enum class AppointmentStatus {
    PENDING,
    COMPLETE,
    CANCELLED
}