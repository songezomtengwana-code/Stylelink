package com.ekasi.studios.stylelink.ui.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ekasi.studios.stylelink.base.common.composables.black20
import com.ekasi.studios.stylelink.base.common.composables.white100
import com.ekasi.studios.stylelink.base.components.ActionIconButton
import com.ekasi.studios.stylelink.base.components.LoadingDialog
import com.ekasi.studios.stylelink.base.components.SolidNavbar
import com.ekasi.studios.stylelink.base.composable.Empty
import com.ekasi.studios.stylelink.base.composable.SectionTitle
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
                        SectionTitle(
                            title = "Favourites",
                            count = success.user.favorites.count(),
                            moreAction = {}
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
                        SectionTitle(
                            title = "Booking History",
                            count = success.user.favorites.size,
                            moreAction = {}
                        )
                        SectionTitle(
                            title = "More Stores",
                            count = 6,
                            moreAction = {}
                        )

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
            Row(
                modifier = Modifier
                    .padding(0.dp, 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Fetching Stores", style = MaterialTheme.typography.labelSmall)
                LinearProgressIndicator()
            }
        }

        is StoreState.Success -> {
            val success = (state.value as StoreState.Success)
            Column(

            ) {
                success.stores.forEach { store: Store ->
                    StoreCard(
                        store = store,
                        navController = navController
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

@Composable
fun StoreCard(store: Store, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, tinySize)
            .clickable { }

    ) {
        Card(
            modifier = Modifier
                .clickable { }
        ) {
            AsyncImage(model = store.profileImage, contentDescription = store.name)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 4.dp)
        ) {
            when (store.isActive) {
                true -> {
                    Text(
                        text = "Active",
                        color = Color.Green,
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                false -> {
                    Card(
                        modifier = Modifier
                            .padding(0.dp, 12.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.LightGray
                        )
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp, 2.dp),
                            text = "offline",
                            color = Color.Black,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                else -> Text(
                    text = "Smoking my nappy dreads",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Text(
                text = store.name.capitalize(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.LocationOn,
                    contentDescription = "location_on_ion_location",
                    modifier = Modifier
                        .height(tinySize),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = store.address.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
            }
            Row {
                Text(text = "Business Hours: ${store.businessHours!![0]} - ${store.businessHours[1]}")
            }
            Row {
                val storeProfileRoute =
                    Screen.StoreProfile.route.replace("{storeId}", store._id.toString())
                Button(
                    onClick = { navController.navigate(storeProfileRoute) },
                ) {
                    Text(text = "Visit Profile")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.Share, contentDescription = "share_icon")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "favorite_icon"
                    )
                }
            }
        }
    }
}