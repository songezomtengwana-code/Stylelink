package com.ekasi.studios.stylelink.data.repository

import android.content.Context
import android.util.Log
import com.ekasi.studios.stylelink.utils.network.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.serialization.json.buildJsonObject
import org.slf4j.MDC.put

class SupabaseAuthRepository(context: Context) {
    val supabaseClient = SupabaseClient()

    suspend fun register(email: String, password: String): UserInfo? {
        val user = supabaseClient.supabase.auth.signUpWith(Email) {
            email
            password
        }

        if (user !== null) {
            Log.v("supabaseSignUp", "response: $user")
            return user
        } else {
            return null;
        }
    }

    suspend fun login(email: String, password: String): Unit? {
        val user = supabaseClient.supabase.auth.signInWith(Email) {
            email
            password
        }

        if (user !== null) {
            Log.v("supabaseSignUp", "response: $user")
            return user
        } else {
            return null;
        }
    }
}