package com.ekasi.studios.stylelink.utils.services

import com.ekasi.studios.stylelink.data.model.RegistrationUserModel
import com.ekasi.studios.stylelink.data.model.ServerUserModel
import com.ekasi.studios.stylelink.data.model.responses.RegisterUserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {
    @POST("api/users/register")
    suspend fun registerNewUserAccount(@Body body: RegistrationUserModel): RegisterUserResponse
    @GET("api/users/{id}")
    suspend fun getSingleUserAccount(@Path("id") id: String): ServerUserModel
}