package com.ekasi.studios.stylelink.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ekasi.studios.stylelink.R
import com.ekasi.studios.stylelink.ui.theme.StylelinkTheme
import com.ekasi.studios.stylelink.ui.theme.mediumSize

private const val splashTimeOut: Long = 1500 // note -> milliseconds

@Composable
fun SplashScreen(navController: NavController, viewModel: SplashViewModel) {
    Scaffold(
        bottomBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(0.dp, 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "App Version 1.0.0",
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.stylelink_large_clear),
                    contentDescription = "stylelink_logo_white",
                    modifier = Modifier
                        .height(mediumSize)
                )
                LinearProgressIndicator(
                    modifier = Modifier.width(mediumSize),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.primary,
                )
            }

            LaunchedEffect(Unit) {
//                kotlinx.coroutines.delay(splashTimeOut)
//                navController.navigate(Screen.Signup.route)

                    viewModel.checkForUserActivity()
            }
        }

    )


}

@Preview
@Composable
fun SplashScreenPreview() {
    StylelinkTheme {
//        SplashScreen(navController = rememberNavController())
    }
}