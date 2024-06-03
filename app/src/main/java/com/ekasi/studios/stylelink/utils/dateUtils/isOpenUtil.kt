package com.ekasi.studios.stylelink.utils.dateUtils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun isOpen(businessHours: List<String>): String {
    // Parse the opening and closing times
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val openingTime = LocalTime.parse(businessHours[0], formatter)
    val closingTime = LocalTime.parse(businessHours[1], formatter)

    // Get the current time
    val currentTime = LocalTime.now()

    // Check if the current time is within the business hours
    return if (currentTime.isAfter(openingTime) && currentTime.isBefore(closingTime)) {
        "open"
    } else {
        "closed"
    }
}