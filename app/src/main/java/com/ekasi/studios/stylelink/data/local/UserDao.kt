package com.ekasi.studios.stylelink.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ekasi.studios.stylelink.data.model.ServerUserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: ServerUserModel)
    @Update
    suspend fun  update(user: ServerUserModel)
    @Query("SELECT * from users")
    fun getAllUsers(): Flow<List<ServerUserModel>>
}