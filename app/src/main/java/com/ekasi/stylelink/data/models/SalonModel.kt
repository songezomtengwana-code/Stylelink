package com.ekasi.stylelink.data.models

data class SalonModel(
    val id: String,
    val index: String,
    val guid: String,
    val isActive: Boolean,
    val profileImageURL: String,
    val name: String,
    val address: String,
    val email: String,
    val code:  String,
    val phone: String,
    val website: String,
    val rating: Float,
    val servicesFor: String,
    val about: String,
    val longitude: Float,
    val latitude: Float,
    val yearsOfExperience: Int,
    val registered: String,
    val isSuspended: Boolean,
    val tags:  List<String>?,
    val servicesOffered: List<String>,
    val businessHours: List<String>,
    val socialMediaLink: Map<String, String>,
    val images: List<String>
) {
    data class Hairstylist(
        val stylistId: String,
        val name: String,
        val specialties: List<String>,
        val yearsOfExperience: Int,
        val profileImageURL: String
    )
}
