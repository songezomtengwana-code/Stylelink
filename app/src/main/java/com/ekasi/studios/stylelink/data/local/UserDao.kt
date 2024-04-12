package com.ekasi.studios.stylelink.data.local

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity)
    @Update
    suspend fun  update(user: UserEntity)
    @Query("SELECT * from users")
    fun getAllUsers(): Flow<List<UserEntity>>
}