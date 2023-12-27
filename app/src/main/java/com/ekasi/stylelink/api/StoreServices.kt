package com.ekasi.stylelink.api

import com.ekasi.stylelink.data.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreServices {
    @GET("/api/users/{email}")
    fun getUserSingleAccount(@Path("email") email: String): Call<UserModel>
}