package com.ekasi.studios.stylelink.data.repository

import com.ekasi.studios.stylelink.data.model.RegistrationUserModel
import com.ekasi.studios.stylelink.data.model.ServerUserModel
import com.ekasi.studios.stylelink.data.model.auth.AuthSuccessResponse
import com.ekasi.studios.stylelink.data.model.auth.Login
import com.ekasi.studios.stylelink.data.model.responses.RegisterUserResponse
import com.ekasi.studios.stylelink.utils.services.UserApiService
import com.ekasi.studios.stylelink.utils.services.auth.AuthService

class UserRepository(
    private val userApiService: UserApiService,
    private val authService: AuthService
) {

    suspend fun createUserAccount(user: RegistrationUserModel): RegisterUserResponse {
        return userApiService.registerNewUserAccount(user)
    }

    suspend fun getUserAccount(id: String): ServerUserModel {
        return userApiService.getSingleUserAccount(id)
    }

    suspend fun signInUser(email: String, password: String): AuthSuccessResponse {
        return authService.signIn(Login(email, password))
    }
}