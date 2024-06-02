package com.ekasi.studios.stylelink.utils.services.auth

import com.ekasi.studios.stylelink.data.model.auth.AuthSuccessResponse
import com.ekasi.studios.stylelink.data.model.auth.Login
import retrofit2.http.Body
import retrofit2.http.POST

private const val LOGIN = "api/login"

interface AuthService {
    @POST(LOGIN)
    suspend fun signIn(@Body body: Login): AuthSuccessResponse
}