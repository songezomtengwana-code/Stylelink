package com.ekasi.studios.stylelink.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ekasi.studios.stylelink.ui.login.LoginScreen
import com.ekasi.studios.stylelink.ui.main.MainScreen
import com.ekasi.studios.stylelink.ui.main.MainViewModel
import com.ekasi.studios.stylelink.ui.register.RegisterScreen
import com.ekasi.studios.stylelink.ui.register.RegisterViewModel
import com.ekasi.studios.stylelink.ui.signup.SignupScreen
import com.ekasi.studios.stylelink.ui.signup.SignupViewModel
import com.ekasi.studios.stylelink.ui.splash.SplashScreen
import com.ekasi.studios.stylelink.ui.splash.SplashViewModel
import com.ekasi.studios.stylelink.ui.login.LoginViewModel
import com.ekasi.studios.stylelink.ui.search.SearchScreen
import com.ekasi.studios.stylelink.ui.search.SearchViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    registerViewModel: RegisterViewModel,
    signupViewModel: SignupViewModel,
    splashScreenViewModel: SplashViewModel,
    mainViewModel: MainViewModel,
    loginViewModel: LoginViewModel,
    searchViewModel: SearchViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController, splashScreenViewModel)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(registerViewModel)
        }
        composable(route = Screen.Main.route) {
            MainScreen(mainViewModel)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(loginViewModel)
        }
        composable(route = Screen.Signup.route) {
            SignupScreen(viewModel = signupViewModel)
        }
        composable(route = Screen.Search.route) {
            SearchScreen(viewModel = searchViewModel)
        }
    }
}