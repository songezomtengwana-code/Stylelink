package com.ekasi.studios.stylelink.utils.services.services

import com.ekasi.studios.stylelink.data.model.Service
import retrofit2.http.GET
import retrofit2.http.Path

private const val GET_SINGLE = "api/services/{id}"
private const val GET_ALL_BY_STORE = "api/services/store/{storeId}"

interface ServicesService {
    /**
     * returns a list of services by storeId for specified store
     *
     * @param storeId String
     */
    @GET(GET_ALL_BY_STORE)
    suspend fun fetchServicesByStore(@Path("storeId") storeId: String) : List<Service>

    /**
     * returns a single services by id
     *
     * @param id String
     */
    @GET(GET_SINGLE)
    suspend fun fetchServiceById(@Path("id") id: String) : Service
}