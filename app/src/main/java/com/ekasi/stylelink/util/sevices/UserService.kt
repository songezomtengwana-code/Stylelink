package com.ekasi.stylelink.util.sevices

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.ekasi.stylelink.data.models.UserModel

class UserService : Service() {
    private var userPreferences: UserModel? = null

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    inner class UserBinder(private val service: UserService): Binder(){
        fun getUserPreferences(): UserModel? = userPreferences
        fun setUserPreferences(user: UserModel) {
            userPreferences = user
            Log.d("User Service", "User Set Successfully")
        }

        fun loadUserPreferencesFromDatabase() {
            // TODO
        }
    }
}