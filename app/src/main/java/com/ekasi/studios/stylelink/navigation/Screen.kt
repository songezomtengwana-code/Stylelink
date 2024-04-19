package com.ekasi.studios.stylelink.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash_screen")
    data object Main : Screen("main_screen")
    data object Register : Screen("register_screen")
    data object Login : Screen("login_screen")
    data object Signup : Screen("signup_screen")
    data object Search : Screen("search_screen")
//    object Home : Screen("main_screen")
}