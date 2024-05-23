package com.ekasi.studios.stylelink.data.converters

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// FavoritesTypeConverter.kt
//@TypeConverter
@ProvidedTypeConverter
class ListConverter {
    @TypeConverter
    fun fromList(favorites: List<String>): String {
        val gson = Gson()
        return gson.toJson(favorites)
    }

    @TypeConverter
    fun toList(jsonString: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<Any>>() {}.type
        return gson.fromJson(jsonString, type)
    }
}

// UserEntity.kt
//@Entity
//data class UserEntity(
//    @PrimaryKey val id: Int,
//    val name: String,
//    @TypeConverters(ListConverter::class)
//    val favorites: List<Any>?,
//)
