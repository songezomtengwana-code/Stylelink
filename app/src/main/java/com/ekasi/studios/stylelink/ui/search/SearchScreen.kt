package com.ekasi.studios.stylelink.ui.search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.ekasi.studios.stylelink.base.components.LoadingDialog
import com.ekasi.studios.stylelink.base.composable.CarouselEmpty
import com.ekasi.studios.stylelink.navigation.Screen
import com.ekasi.studios.stylelink.ui.theme.tinySize
import com.ekasi.studios.stylelink.viewModels.LocationState
import com.ekasi.studios.stylelink.viewModels.LocationViewModel
import com.ekasi.studios.stylelink.viewModels.PlacesState
import com.ekasi.studios.stylelink.viewModels.PlacesViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    locationViewModel: LocationViewModel,
    placesViewModel: PlacesViewModel
) {
    var searchExpanded by remember { mutableStateOf(true) }
    var searchText by remember { mutableStateOf("") }
    val locationState = locationViewModel.locationState.collectAsState()
    val placesState = placesViewModel.placesState.collectAsState()
    var showBottomSheet by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        placesViewModel.fetchStoreMarkers()
    }

    when (placesState.value) {
        is PlacesState.Loading -> {
            LoadingDialog()
        }

        is PlacesState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error")
                }
            }
        }

        is PlacesState.Success -> {
            val places = (placesState.value as PlacesState.Success).places

            Scaffold(
                topBar = {
                    Column() {
//                        CenteredTopBar(title = "Find Stores") {
//                            viewModel.navigateTo(Screen.Main.route)
//                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(tinySize / 2),
                            horizontalArrangement = Arrangement.spacedBy(
                                tinySize / 4
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(onClick = { viewModel.navigateTo(Screen.Main.route) }) {
                                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "navigation_back")
                            }
                            TextField(
//                                leadingIcon = { Icon(
//                                    imageVector = Icons.Rounded.Search,
//                                    contentDescription = "search_icon"
//                                )},
                                maxLines = 1,
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color(0xFFE2E2E2),
                                    focusedContainerColor = Color(0xFFE2E2E2),
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                ),
                                value = searchText,
                                onValueChange = { searchText = it },
                                modifier = Modifier
                                    .weight(1f),
                                singleLine = true,
                                shape = MaterialTheme.shapes.extraLarge
                            )
                        }
                    }
                },
            ) { paddingValues ->
                when (locationState.value) {
                    is LocationState.PermissionDenied -> {
                        Text(text = "Location Permission Have Been Denied")
                    }

                    is LocationState.Coordinates -> {
                        val coordinates =
                            (locationState.value as LocationState.Coordinates).coordinates
                        val lastLocation = LatLng(coordinates.first, coordinates.second)
                        val cameraPosition = rememberCameraPositionState {
                            position = CameraPosition.fromLatLngZoom(lastLocation, 13f)
                        }

                        LaunchedEffect(Unit) {
                            viewModel.updateLastLocation(coordinates)
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                GoogleMap(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(paddingValues),
                                    cameraPositionState = cameraPosition,
                                    onMapClick = { searchExpanded = false }
                                ) {
                                    if (places.isNotEmpty()) {
                                        places.forEach { place ->
                                            Marker(
                                                state = MarkerState(
                                                    LatLng(
                                                        place.coordinates.latitude,
                                                        place.coordinates.longitude
                                                    )
                                                ),
                                                title = place.storeName,
                                                tag = place.storeName,
                                            )
                                        }
                                    }

                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(tinySize, tinySize, 0.dp, 0.dp))
                                    .animateContentSize()
                                    .height(
                                        if (showBottomSheet) LocalConfiguration.current.screenHeightDp.dp / 3
                                        else 70.dp
                                    )
                                    .background(Color.Transparent),
                            ) {
                                Scaffold() { paddingValues ->
                                    Column(
                                        modifier = Modifier
                                            .background(MaterialTheme.colorScheme.background)
                                            .padding(paddingValues)
                                            .verticalScroll(rememberScrollState())
                                    ) {
                                        if (places.isNotEmpty()) {
                                            places.forEach { place ->
                                                Text(place.markerAddress)
                                                Text(place.storeId)
                                                Spacer(modifier = Modifier.height(tinySize))
                                            }
                                        }
                                    }
                                }

                            }
                            if (showBottomSheet) {

                            }
                        }
                    }

                    else -> {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CarouselEmpty(
                                text = "Location Permissions Required for a working",
                                suggestion = "Allow location permissions in settings to proceed"
                            )
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bottom() {
    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = mutableStateOf(true)
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            showBottomSheet.value = false
        },
//                                scrimColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
//            if (places.isNotEmpty()) {
//                places.forEach { place ->
//                    Text(place.markerAddress)
//                    Text(place.storeId)
//                }
//            }
        }
    }
}

