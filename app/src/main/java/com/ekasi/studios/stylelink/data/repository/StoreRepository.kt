package com.ekasi.studios.stylelink.data.repository

import com.ekasi.studios.stylelink.utils.services.stores.StoreService
import com.ekasi.studios.stylelink.utils.services.stores.models.Store

class StoreRepository(
    private val storeService: StoreService
) {

    suspend fun fetchStores(): List<Store> {
        return storeService.fetchStores()
    }

    suspend fun fetchStoreProfile(id: String): Store {
        return storeService.fetchStoreById(id)
    }
}