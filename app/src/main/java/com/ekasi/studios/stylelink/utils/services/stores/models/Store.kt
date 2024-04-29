package com.ekasi.studios.stylelink.utils.services.stores.models

data class Store(
    // server instantiated
    val id: String? = "",
    val index: String? = "",
    val guid: String? = "",
    // required for inserting store data
    val profileImage: String,
    val name: String,
    val servicesFor: String,
    val company: String,
    val email: String,
    // nullable values
    val isActive: Boolean? = false,
    val services: List<String> = emptyList(),  // will store and potentially return IDs for the referenced services
    val rating: Float = 0f,
    val servicesOffered: List<String>? = emptyList(),
    val businessHours: List<String>? = listOf("09:00", "17:00"),
    val socialMediaLinks: List<StoreSocialMediaLink>? = emptyList(),
    val images: List<StoreSocialMediaLink>? = emptyList(),
    val isSuspended: Boolean? = false,
    val staff: List<String>? = emptyList(),  // Assuming stylist references are converted to IDs
    val posts: List<String>? = emptyList(),  // Assuming post references are converted to IDs
    val code: String = "+27",
    val phone: String? = null,  // Nullable as phone is not required
    val address: String? = "",
    val about: String? = null,  // Nullable as about is not required
    val registered: String,
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val tags: List<String>? = emptyList()
)