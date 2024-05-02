package com.ekasi.studios.stylelink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.ekasi.studios.stylelink.data.converters.ListConverter
import com.ekasi.studios.stylelink.data.local.AppDatabase
import com.ekasi.studios.stylelink.data.repository.AuthRepository
import com.ekasi.studios.stylelink.data.repository.ServicesRepository
import com.ekasi.studios.stylelink.data.repository.StoreRepository
import com.ekasi.studios.stylelink.data.repository.UserRepository
import com.ekasi.studios.stylelink.navigation.SetupNavGraph
import com.ekasi.studios.stylelink.ui.login.LoginViewModel
import com.ekasi.studios.stylelink.ui.main.MainViewModel
import com.ekasi.studios.stylelink.ui.register.RegisterViewModel
import com.ekasi.studios.stylelink.ui.search.SearchViewModel
import com.ekasi.studios.stylelink.ui.signup.SignupViewModel
import com.ekasi.studios.stylelink.ui.splash.SplashViewModel
import com.ekasi.studios.stylelink.ui.storeprofile.StoreProfileViewModel
import com.ekasi.studios.stylelink.ui.theme.StylelinkTheme
import com.ekasi.studios.stylelink.utils.network.NetworkClient
import com.ekasi.studios.stylelink.viewModels.StoresViewModel
import com.ekasi.studios.stylelink.viewModels.UserViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {
    companion object {
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locationPermissionGranted by mutableStateOf(false)

        appDatabase =
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, "stylelink.db")
                .addTypeConverter(ListConverter())
                .build()

        setContent {
            StylelinkTheme {
                // Navigation Controller
                val navController = rememberNavController()

                // Firebase
                val auth = Firebase.auth

                // Repositories
                val userRepository = UserRepository(NetworkClient.NetworkClient.userApiService())
                val storesRepository = StoreRepository(NetworkClient.NetworkClient.storesApiService())
                val servicesRepository = ServicesRepository(NetworkClient.NetworkClient.servicesApiService())

                // ViewModels
                val userViewModel = UserViewModel(
                    userDao = appDatabase.userDao(),
                    application = application,
                    userRepository = userRepository)

                val signupViewModel = SignupViewModel(
                    authRepository = AuthRepository(auth),
                    navController = navController
                )

                val splashViewModel = SplashViewModel(
                    authRepository = AuthRepository(auth),
                    navController = navController
                )

                val registerViewModel = RegisterViewModel(
                    userRepository = userRepository,
                    navController = navController,
                    userViewModel = userViewModel,
                    authRepository = AuthRepository(auth)
                )

                val mainViewModel = MainViewModel(
                    authRepository = AuthRepository(auth),
                    navController = navController,
                    userViewModel = userViewModel
                )

                val loginViewModel = LoginViewModel(
                    authRepository = AuthRepository(auth),
                    navController = navController
                )

                val storesViewModel = StoresViewModel(
                    storeRepository = storesRepository,
                )

                val searchViewModel = SearchViewModel(navController, application)

                val storeProfileViewModel = StoreProfileViewModel(
                    storeRepository = storesRepository,
                    servicesRepository = servicesRepository,
                    navController = navController,
                )

                SetupNavGraph(
                    navController = navController,
                    splashScreenViewModel = splashViewModel,
                    signupViewModel = signupViewModel,
                    registerViewModel = registerViewModel,
                    mainViewModel = mainViewModel,
                    loginViewModel = loginViewModel,
                    searchViewModel = searchViewModel,
                    storesViewModel = storesViewModel,
                    storeProfileViewModel = storeProfileViewModel
                )
            }
        }

        // Construct a PlacesClient
        Places.initialize(applicationContext, getString(R.string.maps_api_key))
        var placesClient = Places.createClient(this)

        // Constructs a FusedLocationProviderClient.
        var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


    }
}
