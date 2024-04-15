package com.ekasi.studios.stylelink.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ekasi.studios.stylelink.data.converters.ListConverter
import com.ekasi.studios.stylelink.data.model.ServerUserModel

@TypeConverters(ListConverter::class)
@Database(entities = [ServerUserModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}