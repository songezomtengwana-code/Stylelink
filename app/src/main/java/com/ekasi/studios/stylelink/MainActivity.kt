package com.ekasi.studios.stylelink

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.navigation.compose.rememberNavController
import com.ekasi.studios.stylelink.base.common.composables.StylelinkTheme
import com.ekasi.studios.stylelink.data.repository.AuthRepository
import com.ekasi.studios.stylelink.data.repository.UserRepository
import com.ekasi.studios.stylelink.navigation.SetupNavGraph
import com.ekasi.studios.stylelink.ui.register.RegisterViewModel
import com.ekasi.studios.stylelink.utils.network.NetworkClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.prefs.Preferences


class MainActivity : ComponentActivity() {
    private val registerViewModel = RegisterViewModel(AuthRepository(Firebase.auth),UserRepository(NetworkClient.NetworkClient.userApiService))
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            StylelinkTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    navController = navController,
                    registerViewModel
                )
            }
        }
    }
}
