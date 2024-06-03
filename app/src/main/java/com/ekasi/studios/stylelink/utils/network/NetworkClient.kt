package com.ekasi.studios.stylelink.utils.network

import com.ekasi.studios.stylelink.utils.services.UserApiService
import com.ekasi.studios.stylelink.utils.services.auth.AuthService
import com.ekasi.studios.stylelink.utils.services.markers.MarkerService
import com.ekasi.studios.stylelink.utils.services.products.ProductService
import com.ekasi.studios.stylelink.utils.services.services.ServicesService
import com.ekasi.studios.stylelink.utils.services.stores.StoreService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient private constructor() {
    // EKasi.Stylelink.API
    // network connection client to the REST API services
    // source code -> https://github.com/songezomtengwana-code/stylelinkAPI
    // server documentation -> https://stylelinkapi.onrender.com/api/docs
    object NetworkClient {
        private const val BASE_URL = "https://stylelinkapi.onrender.com/"
        private const val LOCAL_BASE_URL = "http://localhost:7000/"

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        fun authService(): AuthService {
            return retrofit.create(AuthService::class.java)
        }

        fun userApiService(): UserApiService {
            return retrofit.create(UserApiService::class.java)
        }

        fun storesApiService(): StoreService {
            return retrofit.create(StoreService::class.java)
        }

        fun servicesApiService(): ServicesService {
            return retrofit.create(ServicesService::class.java)
        }

        fun productsApiService(): ProductService {
            return retrofit.create(ProductService::class.java)
        }

        fun markersApiService(): MarkerService {
            return retrofit.create(MarkerService::class.java)
        }
    }
}