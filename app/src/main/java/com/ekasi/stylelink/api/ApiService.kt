package com.ekasi.stylelink.api

import com.ekasi.stylelink.data.models.NewUserModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/users")
    fun createUserAccount(@Body data: NewUserModel): Call<ResponseBody>
}