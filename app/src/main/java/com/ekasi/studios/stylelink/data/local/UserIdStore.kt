package com.ekasi.studios.stylelink.data.local

import android.content.Context
import androidx.datastore.dataStoreFile
import kotlinx.coroutines.flow.first
import java.io.File
object UserIdStore {
    private const val PREF_KEY_ID = "user_id"

    private suspend fun getPreferencesDataStore(context: Context): File {
        return context.dataStoreFile(fileName = "user_id_prefs")
    }

//    suspend fun saveUserId(context: Context, userId: String) {
//        getPreferencesDataStore(context).edit { preferences ->
//            preferences[PREF_KEY_ID] = userId
//        }
//    }
//
//    suspend fun getUserId(context: Context): String? {
//        val preferences = getPreferencesDataStore(context).data.first()
//        return preferences[PREF_KEY_ID]0
//    }
}