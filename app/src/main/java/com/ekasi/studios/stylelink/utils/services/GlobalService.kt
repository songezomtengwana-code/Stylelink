package com.ekasi.studios.stylelink.utils.services

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive =  true
    }
}