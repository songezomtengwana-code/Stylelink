package com.ekasi.studios.stylelink.data.repository

import com.ekasi.studios.stylelink.data.model.RegistrationUserModel
import com.ekasi.studios.stylelink.data.model.ServerUserModel
import com.ekasi.studios.stylelink.utils.services.UserApiService
import okhttp3.ResponseBody
import retrofit2.Call

class UserRepository(private val userApiService: UserApiService,) {

    suspend fun createUserAccount(user: RegistrationUserModel): Call<ServerUserModel> {
        return userApiService.registerNewUserAccount(user)
    }

    suspend fun getUserAccount(id: String): ServerUserModel {
        return userApiService.getSingleUserAccount(id)
    }
}