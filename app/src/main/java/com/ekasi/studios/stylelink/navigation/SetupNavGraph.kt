package com.ekasi.studios.stylelink.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ekasi.studios.stylelink.ui.login.LoginScreen
import com.ekasi.studios.stylelink.ui.splash.MainScreen
import com.ekasi.studios.stylelink.ui.register.RegisterScreen
import com.ekasi.studios.stylelink.ui.register.RegisterViewModel
import com.ekasi.studios.stylelink.ui.splash.SplashScreen
import com.ekasi.studios.stylelink.viewModels.UserViewModel

@Composable
fun SetupNavGraph(navController: NavHostController, registerViewModel: RegisterViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(registerViewModel, navController = navController)
        }
        composable(route = Screen.Main.route) {
            MainScreen()
        }
        composable(route = Screen.Login.route) {
            LoginScreen()
        }
    }
}