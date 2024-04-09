package com.ekasi.studios.stylelink.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash_screen")
    data object Main : Screen("main_screen")
    data object Register : Screen("register_screen")
//    object Home : Screen("main_screen")
}