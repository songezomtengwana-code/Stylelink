package com.ekasi.studios.stylelink.data.model

data class UserModel(
    val _id: Id?,
    val address: String,
    val city: String?,
    val country: String?,
    val dateOfBirth: String?,
    val email: String,
    val favoriteHairstylist: String?,
    val favorites: List<Any>?,
    val following: List<Any>?,
    val fullname: String,
    val hairColor: String?,
    val hairType: String?,
    val latitude: Latitude?,
    val longitude: Longitude?,
    val password: String,
    val phoneNumber: String,
    val preferredSalon: String?,
    val profileImageURL: String?,
    val registrationDate: RegistrationDate?,
    val state: String?,
    val userId: String,
    val zipCode: String?
)