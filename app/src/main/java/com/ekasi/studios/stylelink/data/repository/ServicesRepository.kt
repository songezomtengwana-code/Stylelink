package com.ekasi.studios.stylelink.data.repository

import com.ekasi.studios.stylelink.data.model.Service
import com.ekasi.studios.stylelink.utils.services.services.ServicesService

class ServicesRepository(
    private val api: ServicesService
) {

    suspend fun fetchStoreServices(storeId: String): List<Service> {
        return api.fetchServicesByStore(storeId)
    }

    suspend fun fetchServiceById(id: String):Service {
        return api.fetchServiceById(id)
    }
}