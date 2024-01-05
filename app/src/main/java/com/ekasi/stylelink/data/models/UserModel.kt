package com.ekasi.stylelink.data.models

data class UserModel(
    val __v: Int,
    val _id: String,
    val address: String,
    val city: String,
    val country: String,
    val dateOfBirth: String,
    val email: String,
    val favoriteHairstylist: String,
    val hairColor: String,
    val hairType: String,
    val interests: List<Any>,
    val latitude: Latitude,
    val longitude: Longitude,
    val password: String,
    val phoneNumber: String,
    val preferredSalon: String,
    val profileImageURL: String,
    val registrationDate: String,
    val state: String,
    val userId: String,
    val username: String,
    val zipCode: String,
    val following: List<Any>
)