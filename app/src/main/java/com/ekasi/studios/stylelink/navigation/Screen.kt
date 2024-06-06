package com.ekasi.studios.stylelink.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash_screen")
    data object Home : Screen("home")
    data object Register : Screen("register_screen")
    data object Login : Screen("login_screen")
    data object Signup : Screen("signup_screen")
    data object Discover : Screen("discover")
    data object StoreProfile : Screen("store/{storeId}")

    // booking flow
    data object Booking : Screen("booking/{serviceId}")
//    object Home : Screen("main_screen")
}