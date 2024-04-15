package com.ekasi.studios.stylelink.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ekasi.studios.stylelink.data.converters.ListConverter
import io.reactivex.annotations.NonNull


@Entity(tableName = "users")
data class ServerUserModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var _id: String,
    var address: String?,
    var city: String?,
    var country: String?,
    var dateOfBirth: String?,
    var email: String,
    var favoriteHairstylist: String?,
    var favorites: List<String>,
    var following: List<String>,
    var fullname: String,
    var hairColor: String?,
    var hairType: String?,
    var latitude: String?,
    var longitude: String?,
    var password: String,
    var phoneNumber: String,
    var preferredSalon: String?,
    var profileImageURL: String?,
    var registrationDate: String?,
    var state: String?,
    var userId: String?,
    var zipCode: String?
)