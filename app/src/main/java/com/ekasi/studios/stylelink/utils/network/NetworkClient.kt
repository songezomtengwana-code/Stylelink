package com.ekasi.studios.stylelink.utils.network

import com.ekasi.studios.stylelink.utils.services.UserApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient private constructor() {
    // network connection client to the RESTful API services
    // server documentation -> https://stylelinkapi.onrender.com/api/docs
    object NetworkClient {
        private const val BASE_URL = "https://stylelinkapi.onrender.com/"

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        fun userApiService(): UserApiService {
            return retrofit.create(UserApiService::class.java)
        }

    }
}