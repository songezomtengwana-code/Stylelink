package com.ekasi.studios.stylelink.data.model

data class Product(
    val name: String,
    val sku: String,
    val description: String,
    val price: Double,
    val productImg: String,
    val storeId: String,
    val _id: String? = "",
    val stockStatus: Boolean = true,
    val stockCount: Int = 0,
    val categories: List<String> = emptyList(),
    val discounted: Boolean = false,
    val discountedBy: Double = 0.0,
)
