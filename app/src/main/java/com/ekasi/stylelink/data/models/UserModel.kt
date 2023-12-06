package com.ekasi.stylelink.data.models

import java.util.Date

data class UserModel(
    val userId: String,
    val username: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val registrationDate: Date,
    val profileImageURL: String? = "",
    val hairType: String? = "",
    val hairColor: String? = "",
    val preferredSalon: String? = "",
    val favoriteHairstylist: String? = "",
    val interests: List<String>? = listOf(),
    val dateOfBirth: String? = "",
    val address: String? = "",
    val city: String? = "",
    val state: String? = "",
    val country: String? = "",
    val zipCode: String? = "",
    val latitude: Double? = 0.0,
    val longitude: Double? =0.0
)