package com.ekasi.studios.stylelink.data.local
//
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import androidx.room.TypeConverters
//import com.ekasi.studios.stylelink.data.converters.ListConverter
//import com.ekasi.studios.stylelink.data.model.IdModel
//import com.ekasi.studios.stylelink.data.model.LatitudeModel
//import com.ekasi.studios.stylelink.data.model.LongitudeModel
//import com.ekasi.studios.stylelink.data.model.RegistrationDateModel
//
//// table configuration for local storage under table name "users"
//// refer to https://medium.com/@kathankraithatha/room-database-in-android-294442467bc8
//
//@Entity(tableName = "users")
//data class ServerUserModel(
//    @PrimaryKey(autoGenerate = false)
//    val _id: String? = "",
//    val address: String? = "",
//    val city: String? = "",
//    val country: String? = "",
//    val dateOfBirth: String? = "",
//    val email: String,
//    val favoriteHairstylist: String? = "",
//    @TypeConverters(ListConverter::class)
//    val favorites: List<String>?,
//    @TypeConverters(ListConverter::class)
//    val following: List<String>?,
//    val fullname: String,
//    val hairColor: String? = "",
//    val hairType: String? = "",
//    val latitude: String?,
//    val longitude: String? = "",
//    val password: String,
//    val phoneNumber: String,
//    val preferredSalon: String? = "",
//    val profileImageURL: String? = "",
//    val registrationDate: String? = "",
//    val state: String? = "",
//    val userId: String? = "",
//    val zipCode: String? = ""
//)