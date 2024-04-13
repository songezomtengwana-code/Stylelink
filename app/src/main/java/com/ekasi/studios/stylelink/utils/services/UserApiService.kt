package com.ekasi.studios.stylelink.utils.services

import com.ekasi.studios.stylelink.data.model.RegistrationUserModel
import com.ekasi.studios.stylelink.data.model.ServerUserModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {
    @POST("api/users/register")
    suspend fun registerNewUserAccount(@Body body: RegistrationUserModel): Call<ServerUserModel>
    @GET("api/users/{userId}")
    suspend fun getSingleUserAccount(@Path("userId") id: String): ServerUserModel
}