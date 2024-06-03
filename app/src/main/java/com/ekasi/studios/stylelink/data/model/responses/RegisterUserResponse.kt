package com.ekasi.studios.stylelink.data.model.responses

data class RegisterUserResponse(
    val message: String,
    val result: String,
    val success: Boolean,
    val token: String,
)