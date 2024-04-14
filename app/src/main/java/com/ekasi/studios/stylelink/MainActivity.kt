package com.ekasi.studios.stylelink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.ekasi.studios.stylelink.ui.theme.StylelinkTheme
import com.ekasi.studios.stylelink.data.repository.AuthRepository
import com.ekasi.studios.stylelink.data.repository.UserRepository
import com.ekasi.studios.stylelink.navigation.SetupNavGraph
import com.ekasi.studios.stylelink.ui.main.MainViewModel
import com.ekasi.studios.stylelink.ui.register.RegisterViewModel
import com.ekasi.studios.stylelink.ui.signup.SignupViewModel
import com.ekasi.studios.stylelink.ui.login.LoginViewModel
import com.ekasi.studios.stylelink.ui.splash.SplashViewModel
import com.ekasi.studios.stylelink.utils.network.NetworkClient
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            StylelinkTheme {
                val navController = rememberNavController()
                val auth = Firebase.auth
                val signupViewModel = SignupViewModel(
                    authRepository = AuthRepository(auth),
                    navController = navController
                )

                val splashViewModel = SplashViewModel(
                    authRepository = AuthRepository(auth),
                    navController = navController
                )

                val registerViewModel = RegisterViewModel(
                    userRepository = UserRepository(NetworkClient.NetworkClient.userApiService()),
                    navController = navController
                )

                val mainViewModel = MainViewModel(
                    authRepository = AuthRepository(auth),
                    navController = navController

                )

                val loginViewModel = LoginViewModel(
                    authRepository = AuthRepository(auth),
                    navController = navController
                )


                SetupNavGraph(
                    navController = navController,
                    splashScreenViewModel = splashViewModel,
                    signupViewModel = signupViewModel,
                    registerViewModel = registerViewModel,
                    mainViewModel = mainViewModel,
                    loginViewModel = loginViewModel
                )
            }
        }
    }
}
