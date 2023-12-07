package com.ekasi.stylelink.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient  private constructor() {
    object NetworkClient {
        private const val BASE_URL = "https://stylelinkapi.onrender.com/api/"
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        val apiService: ApiService = retrofit.create(ApiService::class.java)
    }
}