package com.ekasi.studios.stylelink.ui.screens.discover

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ekasi.studios.stylelink.R
import com.ekasi.studios.stylelink.base.composable.CarouselEmpty
import com.ekasi.studios.stylelink.ui.theme.tinySize
import com.ekasi.studios.stylelink.viewModels.LocationState
import com.ekasi.studios.stylelink.viewModels.LocationViewModel
import com.ekasi.studios.stylelink.viewModels.PlacesState
import com.ekasi.studios.stylelink.viewModels.PlacesViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
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

    Scaffold(
        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { /* do something */ },
//            ) {
//                Icon(Icons.Outlined.LocationOn, "Localized description")
//            }
        }
    ) { paddingValues ->
        when (locationState.value) {
            is LocationState.PermissionDenied -> {
                Text(text = "Location Permission Have Been Denied")
            }

            is LocationState.Coordinates -> {

                val coordinates =
                    (locationState.value as LocationState.Coordinates).coordinates
                var cameraPosition = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(viewModel.lastLocation.value, 10f)
                }
                LaunchedEffect(viewModel.lastLocation.value) {
                    val newLocation = viewModel.lastLocation.value
                    if (newLocation != null) {
                        cameraPosition.animate(
                            update = CameraUpdateFactory.newCameraPosition(
                                CameraPosition.fromLatLngZoom(
                                    LatLng(newLocation.latitude, newLocation.longitude),
                                    10f,
                                )
                            ),
                            durationMs = 1000
                        )
                    }
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
                            when (placesState.value) {
                                is PlacesState.Loading -> {
                                    Log.d("placesState", "Loading...")
                                }

                                is PlacesState.Error -> {
                                    Log.d("placesState", "Loading...")
                                }

                                is PlacesState.Success -> {
                                    val places = (placesState.value as PlacesState.Success).places

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
                                            icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_circle),
                                            onClick = { marker ->
                                                viewModel.updateLastLocation(
                                                    Pair(
                                                        marker.position.latitude,
                                                        marker.position.longitude
                                                    )
                                                )
                                                true
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        LocationHeader(
                            onBackIconClick = { viewModel.back() },
                            onMyLocationClick = { viewModel.updateLastLocation(coordinates) },
                            viewModel = viewModel
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(tinySize)
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { /* Do something! */ },
                            contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                        ) {
                            Icon(
                                Icons.Outlined.FilterAlt,
                                contentDescription = "Localized description",
                                modifier = Modifier.height(ButtonDefaults.IconSize)
                            )
                            Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                            Text("Filter")
                        }
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

@Composable
private fun LocationHeader(
    onBackIconClick: () -> Unit = {},
    onMyLocationClick: () -> Unit = {},
    viewModel: DiscoverViewModel,
    myLocation: LatLng? = null
) {
    val gradient = Brush.linearGradient(
        colors = listOf(Color.White, Color.Transparent),
        start = Offset.Zero,
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(tinySize),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onBackIconClick,
            modifier = Modifier
                .clip(CircleShape),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Icon(imageVector = Icons.Rounded.ArrowBackIosNew, contentDescription = "back_icon")
        }
        Button(
            onClick = onMyLocationClick,
            modifier = Modifier
                .clip(CircleShape),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "back_icon",
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "My location",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}