package com.ekasi.studios.stylelink.data.repository

import com.ekasi.studios.stylelink.data.model.Product
import com.ekasi.studios.stylelink.utils.services.products.ProductService

class ProductRepository(
    private val productService: ProductService
){

    suspend fun fetchProductById(id: String): Product {
        return productService.getProductById(id)
    }

    suspend fun fetchProductsByStoreId(storeId: String): List<Product> {
        return productService.getProductsByStoreId(storeId)
    }

}