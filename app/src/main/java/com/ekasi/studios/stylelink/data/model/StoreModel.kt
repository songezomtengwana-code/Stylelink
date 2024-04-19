package com.ekasi.studios.stylelink.data.model

data class StoreModel(
    val _id: String,
    val address: String,
    val businessHours: List<String>,
    val code: String,
    val company: String,
    val email: String,
    val guid: String,
    val id: String,
    val images: List<Any>,
    val index: String,
    val isActive: Boolean,
    val isSuspended: Boolean,
    val latitude: String,
    val longitude: String,
    val name: String,
    val posts: List<Any>,
    val profileImage: String,
    val rating: Int,
    val registered: String,
    val services: List<Any>,
    val servicesFor: String,
    val servicesOffered: List<Any>,
    val socialMediaLinks: List<Any>,
    val staff: List<Any>,
    val tags: List<Any>
)