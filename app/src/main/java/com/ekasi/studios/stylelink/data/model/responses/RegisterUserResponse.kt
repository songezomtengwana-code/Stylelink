package com.ekasi.studios.stylelink.data.model.responses

import com.ekasi.studios.stylelink.data.model.ServerUserModel

data class RegisterUserResponse(
    val message: String,
    val result: ServerUserModel,
    val success: Boolean
)