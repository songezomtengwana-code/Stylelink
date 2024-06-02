package com.ekasi.studios.stylelink.ui.screens.discover

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.ekasi.studios.stylelink.base.components.LoadingDialog
import com.ekasi.studios.stylelink.base.composable.CarouselEmpty
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
fun DiscoverScreen(
    viewModel: DiscoverViewModel,
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
                            position = CameraPosition.fromLatLngZoom(lastLocation, 10f)
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
                                LocationHeader(onBackIconClick = { viewModel.back() })
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

@Composable
private fun LocationHeader(onBackIconClick: ()-> Unit = {}) {
    val gradient = Brush.linearGradient(
        colors = listOf(Color.White, Color.Transparent),
        start = Offset.Zero,
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
//            .background(brush = gradient)
            .padding(tinySize, tinySize * 2, tinySize, tinySize),
        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                tinySize / 4
            )
        ) {
//            Text(
//                text = "Fort Beaufort",
//                style = MaterialTheme.typography.titleLarge,
//                fontFamily = poppinsFontFamily,
//                fontWeight = FontWeight.Bold
//            )
//            Icon(
//                imageVector = Icons.Outlined.KeyboardArrowDown,
//                contentDescription = "navigation_forward",
//            )

            IconButton(onClick = onBackIconClick) {
                Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription ="back_icon")
            }
        }
    }
}