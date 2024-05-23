package com.ekasi.studios.stylelink.utils.services.products

import com.ekasi.studios.stylelink.data.model.Product
import retrofit2.http.GET
import retrofit2.http.Path

private const val GET_PRODUCTS_BY_ID = "api/products/{id}"
private const val GET_ALL_PRODUCTS = "api/products"
private const val GET_PRODUCTS_BY_STORE_ID = "api/products/store/{storeId}"

interface ProductService {

    @GET(GET_PRODUCTS_BY_ID)
    suspend fun getProductById(@Path("id") id: String): Product

    @GET(GET_PRODUCTS_BY_STORE_ID)
    suspend fun getProductsByStoreId(@Path("storeId") storeId: String): List<Product>
}