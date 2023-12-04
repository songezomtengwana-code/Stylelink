package com.ekasi.stylelink.data.models

data class UserModel(
    val userId: String,
    val username: String,
    val email: String,
    val password: String,
    val fullName: String,
    val phoneNumber: String,
    val profileImageURL: String?,
    val hairType: String?,
    val hairColor: String?,
    val preferredSalon: String?,
    val favoriteHairstylist: String?,
    val interests: List<String>,
    val dateOfBirth: String?,
    val registrationDate: String,
    val address: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val zipCode: String?,
    val latitude: Double?,
    val longitude: Double?
)