package com.ekasi.studios.stylelink.data.model

data class RegistrationUserModel(
    val fullname: String,
    val email: String,
    val password: String,
    val phoneNumber: String? = "",
    val address: String? = "",
    val city: String? = "",
    val state: String? = "",
    val profileImageUrl: String? = "",
)