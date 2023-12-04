package com.ekasi.stylelink.data.models

data class SalonModel(
    val salonId: String,
    val salonName: String,
    val salonAddress: String,
    val salonEmail: String,
    val phoneNumber: String,
    val salonWebsite: String,
    val rating: Float,
    val profileImageUrl: String,
    val yearsOfExperience: Int,
    val servicesOffered: List<String>,
    val businessHours: Map<String, String>,
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
