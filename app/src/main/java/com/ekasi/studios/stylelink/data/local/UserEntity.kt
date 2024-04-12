package com.ekasi.studios.stylelink.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ekasi.studios.stylelink.data.model.IdModel
import com.ekasi.studios.stylelink.data.model.LatitudeModel
import com.ekasi.studios.stylelink.data.model.LongitudeModel
import com.ekasi.studios.stylelink.data.model.RegistrationDateModel

// table configuration for local storage under table name "users"
// refer to https://medium.com/@kathankraithatha/room-database-in-android-294442467bc8
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val _id: IdModel?,
    val email: String,
    val fullname: String,
    val phoneNumber: String,
    val profileImageURL: String?,
    val userId: String,
)
