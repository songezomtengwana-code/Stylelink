package com.ekasi.studios.stylelink.ui.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ekasi.studios.stylelink.ui.theme.black20
import com.ekasi.studios.stylelink.ui.theme.white100
import com.ekasi.studios.stylelink.base.components.ActionIconButton
import com.ekasi.studios.stylelink.base.components.LoadingDialog
import com.ekasi.studios.stylelink.base.components.SolidNavbar
import com.ekasi.studios.stylelink.base.composable.CarouselEmpty
import com.ekasi.studios.stylelink.base.composable.CarouselLoader
import com.ekasi.studios.stylelink.base.composable.HomeSearchButton
import com.ekasi.studios.stylelink.base.composable.SectionTitle
import com.ekasi.studios.stylelink.base.widgets.StoreCard
import com.ekasi.studios.stylelink.data.model.ServerUserModel
import com.ekasi.studios.stylelink.navigation.Screen
import com.ekasi.studios.stylelink.ui.theme.tinySize
import com.ekasi.studios.stylelink.utils.services.stores.models.Store
import com.ekasi.studios.stylelink.viewModels.StoreState
import com.ekasi.studios.stylelink.viewModels.StoresViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    storesViewModel: StoresViewModel,
    navController: NavController
) {
    val user: ServerUserModel? by mutableStateOf(viewModel.user)
    val uiState = viewModel.uiState.collectAsState()
    val storesState = storesViewModel.storeState.collectAsState()

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
        topBar = { SolidNavbar(imageUrl = if (user !== null) user?.profileImageURL!! else "") }
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
                        HomeSearchButton()
                        SectionTitle(
                            title = "Favourites",
                            count = success.user.favorites.count(),
                            moreAction = {}
                        )
                        if (success.user.favorites.isNotEmpty()) {
                            val favorites = success.user.favorites

                            favorites.forEach { favorite: String -> Text(text = favorite) }
                        } else {
                            CarouselEmpty(
                                text = "No Favorites Yet.",
                                suggestion = "Click on the heart icon to save a store as a favorite"
                            )
                        }
                        StoreSlider(
                            storesViewModel = storesViewModel,
                            navController = navController
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
fun StoreSlider(storesViewModel: StoresViewModel, navController: NavController) {
    val state = storesViewModel.storeState.collectAsState()

    when (state.value) {
        is StoreState.Loading -> {
            CarouselLoader(text = "Loading Stores...")
        }

        is StoreState.Success -> {
            val stores = (state.value as StoreState.Success).stores
            SectionTitle(title = "Stores", count = stores.size) {

            }
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(tinySize, 8.dp),
                horizontalArrangement = Arrangement.spacedBy(tinySize)
            ) {
                stores.forEach { store: Store ->
                    val storeProfileRoute =
                        Screen.StoreProfile.route.replace("{storeId}", store._id.toString())
                    StoreCard(
                        store = store,
                        onClick = { navController.navigate(storeProfileRoute) }

                    )
                }
            }
        }

        is StoreState.Error -> {
            Column(
                modifier = Modifier
                    .padding(0.dp, 16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "we ran into a problem,try again",
                    style = MaterialTheme.typography.labelSmall
                )
                TextButton(onClick = {
                    storesViewModel.reload()
                }) {
                    Text("Reload")
                }
                Log.d("MainScreen", "error: ${state.value}")
            }
        }
    }
}

