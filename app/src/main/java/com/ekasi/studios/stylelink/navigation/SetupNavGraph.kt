package com.ekasi.studios.stylelink.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ekasi.studios.stylelink.base.components.StorePreview.StorePreviewViewModel
import com.ekasi.studios.stylelink.data.repository.ServicesRepository
import com.ekasi.studios.stylelink.ui.auth.login.LoginScreen
import com.ekasi.studios.stylelink.ui.auth.login.LoginViewModel
import com.ekasi.studios.stylelink.ui.auth.register.RegisterScreen
import com.ekasi.studios.stylelink.ui.auth.register.RegisterViewModel
import com.ekasi.studios.stylelink.ui.auth.signup.SignupScreen
import com.ekasi.studios.stylelink.ui.auth.signup.SignupViewModel
import com.ekasi.studios.stylelink.ui.booking.BookingScreen
import com.ekasi.studios.stylelink.ui.booking.BookingViewModel
import com.ekasi.studios.stylelink.ui.booking.services.BookingRepository
import com.ekasi.studios.stylelink.ui.screens.discover.DiscoverScreen
import com.ekasi.studios.stylelink.ui.screens.discover.DiscoverViewModel
import com.ekasi.studios.stylelink.ui.screens.home.HomeScreen
import com.ekasi.studios.stylelink.ui.screens.home.HomeViewModel
import com.ekasi.studios.stylelink.ui.splash.SplashScreen
import com.ekasi.studios.stylelink.ui.splash.SplashViewModel
import com.ekasi.studios.stylelink.ui.storeprofile.StoreProfile
import com.ekasi.studios.stylelink.ui.storeprofile.StoreProfileViewModel
import com.ekasi.studios.stylelink.utils.network.NetworkClient
import com.ekasi.studios.stylelink.viewModels.LocationViewModel
import com.ekasi.studios.stylelink.viewModels.PlacesViewModel
import com.ekasi.studios.stylelink.viewModels.StoresViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    registerViewModel: RegisterViewModel,
    signupViewModel: SignupViewModel,
    splashScreenViewModel: SplashViewModel,
    homeViewModel: HomeViewModel,
    loginViewModel: LoginViewModel,
    discoverViewModel: DiscoverViewModel,
    storesViewModel: StoresViewModel,
    storeProfileViewModel: StoreProfileViewModel,
    locationViewModel: LocationViewModel,
    placesViewModel: PlacesViewModel,
    storePreviewViewModel: StorePreviewViewModel
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
        composable(route = Screen.Home.route) {
            HomeScreen(homeViewModel, storesViewModel, navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(loginViewModel)
        }
        composable(route = Screen.Signup.route) {
            SignupScreen(viewModel = signupViewModel)
        }
        composable(route = Screen.Discover.route) {
            DiscoverScreen(
                viewModel = discoverViewModel,
                locationViewModel = locationViewModel,
                placesViewModel = placesViewModel,
                storePreviewViewModel = storePreviewViewModel
            )
        }
        composable(
            route = Screen.StoreProfile.route,
            arguments = listOf(navArgument("storeId") { type = NavType.StringType })
        ) { backStackEntry ->
            StoreProfile(
                storeProfileViewModel = storeProfileViewModel,
                backStackEntry.arguments?.getString("storeId")
            )
        }

        // Booking Flow
        composable(
            route = Screen.Booking.route,
            arguments = listOf(navArgument("serviceId") { type = NavType.StringType })
        ) { backStackEntry ->
            BookingScreen(
                serviceId = backStackEntry.arguments?.getString("serviceId"),
                viewModel = BookingViewModel(
                    servicesRepository = ServicesRepository(NetworkClient.NetworkClient.servicesApiService()),
                    bookingRepository = BookingRepository()
                )
            )
        }
    }
}