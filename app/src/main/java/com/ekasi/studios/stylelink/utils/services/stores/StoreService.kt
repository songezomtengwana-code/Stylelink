package com.ekasi.studios.stylelink.utils.services.stores

import com.ekasi.studios.stylelink.utils.services.stores.models.Store
import retrofit2.http.GET
import retrofit2.http.Path

private const val FETCH_STORES_URL = "api/stores";
private const val FETCH_SINGLE_STORE_URL = "api/stores/{id}";
interface StoreService {
    @GET(FETCH_STORES_URL)
    suspend fun fetchStores() : List<Store>
    @GET(FETCH_SINGLE_STORE_URL)
    suspend fun fetchStoreById(@Path("id") id: String): Store
}