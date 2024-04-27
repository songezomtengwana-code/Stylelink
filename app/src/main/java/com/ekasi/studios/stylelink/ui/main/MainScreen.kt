package com.ekasi.studios.stylelink.ui.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ekasi.studios.stylelink.base.common.composables.black20
import com.ekasi.studios.stylelink.base.common.composables.white100
import com.ekasi.studios.stylelink.base.components.ActionIconButton
import com.ekasi.studios.stylelink.base.components.LoadingDialog
import com.ekasi.studios.stylelink.base.components.SolidNavbar
import com.ekasi.studios.stylelink.base.composable.Empty
import com.ekasi.studios.stylelink.data.model.ServerUserModel
import com.ekasi.studios.stylelink.navigation.Screen

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val user: ServerUserModel? by mutableStateOf(viewModel.user)
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (viewModel.user === null) {
            viewModel.fetchUser()
        } else {
            Log.d("LaunchedEffect", viewModel.user.toString())
        }
    }

    Scaffold(
        modifier = Modifier
            .background(white100),
        topBar = { SolidNavbar(imageUrl = if (user !== null ) user?.profileImageURL!! else "")  }
    ) { paddingValues ->

        when (uiState.value) {
            is MainState.Loading -> {
                LoadingDialog()
            }

            is MainState.Success -> {
                val success = (uiState.value as MainState.Success)
                Text(text = success.user.fullname)
                Surface(
                    modifier = Modifier
                        .padding(paddingValues)
                        .background(Color.White)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .background(white100)
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(0.dp, 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                            ) {
                                Text(
                                    text = "Hi, ${success.user.fullname} 👋",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Black
                                )
                                Text(text = "Where to next ?")
                            }
                            ActionIconButton(
                                onClick = { viewModel.navigateTo(Screen.Search.route) },
                                icon = Icons.Rounded.Search,
                                iconSize = 35
                            )
                        }
                        Text(
                            "Favorite Salons (${success.user.favorites.count()})",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        if (success.user.favorites.isNotEmpty()) {
                            val favorites = success.user.favorites

                            favorites.forEach { favorite: String -> Text(text = favorite) }
                        } else {
                            Empty(
                                text = "You don't have any favorites yet :(",
                                suggestion = "Browse available stores that you might like",
                                onClick = {}
                            )
                        }
                        Text(
                            "Bookings History (${success.user.favorites.count()})",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            is MainState.Error -> {
                val error = (uiState.value as MainState.Error)
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "error code: ${viewModel.protoUserDetailsUserId}",
                        style = MaterialTheme.typography.labelSmall,
                        color = black20
                    )
                    Text(error.message)
                    Spacer(modifier = Modifier.height(10.dp))
                    TextButton(
                        onClick = {
                            viewModel.displayLoadingState()
                            viewModel.fetchUser()
                        }
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Refresh,
                                contentDescription = "refresh_icon"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Refresh", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TopHeader(user: ServerUserModel) {
    Row(
        modifier = Modifier
            .padding(0.dp, 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
        ) {
            Text(
                text = "Hi, ${user.fullname} 👋",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black
            )
            Text(text = "Where to next ?")
        }
        ActionIconButton(onClick = { }, icon = Icons.Rounded.Search, iconSize = 24)
    }
}

@Composable
fun PromoSlider(promos: String) {

}