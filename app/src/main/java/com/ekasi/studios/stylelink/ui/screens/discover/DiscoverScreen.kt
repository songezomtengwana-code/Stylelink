package com.ekasi.studios.stylelink.ui.screens.discover

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ekasi.studios.stylelink.R
import com.ekasi.studios.stylelink.base.components.StorePreview.StorePreview
import com.ekasi.studios.stylelink.base.components.StorePreview.StorePreviewViewModel
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel,
    locationViewModel: LocationViewModel,
    placesViewModel: PlacesViewModel,
    storePreviewViewModel: StorePreviewViewModel
) {
    var searchExpanded by remember { mutableStateOf(true) }
    var searchText by remember { mutableStateOf("") }
    val locationState = locationViewModel.locationState.collectAsState()
    val placesState = placesViewModel.placesState.collectAsState()
    var showBottomSheet by remember { mutableStateOf(true) }
    val showModalState = viewModel.showModalState.value
    LaunchedEffect(Unit) {
        placesViewModel.fetchStoreMarkers()
    }

    Scaffold() { paddingValues ->
        when (locationState.value) {
            is LocationState.PermissionDenied -> {
                Text(text = "Location Permission Have Been Denied")
            }

            is LocationState.Coordinates -> {
                val zoom: Float = 15f
                val coordinates =
                    (locationState.value as LocationState.Coordinates).coordinates
                val cameraPosition = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(viewModel.lastLocation.value, 1f)
                }
                LaunchedEffect(Unit, viewModel.lastLocation.value) {
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
                    } else if (viewModel.lastLocation.value == LatLng(0.0, 0.0)) {
                        Log.d("lastLocation", "null")
                    }
                }
                Column {
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
                                    Log.d("placesState", "Error")
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
                                                viewModel.onMarkerClick(place)
                                                false
                                            }
                                        )
                                        Marker(
                                            state = MarkerState(
                                                LatLng(
                                                    coordinates.first,
                                                    coordinates.second
                                                )
                                            ),
                                            title = "Your Locaition",
                                            tag = "This is your current location",
                                            icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_circle_invert),
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
                    val cardHeight = LocalConfiguration.current.screenHeightDp.dp / 4

                    val height by animateDpAsState(
                        targetValue = if (showModalState) cardHeight else 0.dp,
                        label = ""
                    )
                    Column(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .padding(tinySize)
                            .height(height),
                    ) {
                        StorePreview(
                            storeId = viewModel.activeStoreId.value,
                            storePreviewViewModel = storePreviewViewModel,
                            marker = viewModel.marker.value,
                            bookServiceHandler = {viewModel.bookServicesHandler()}
                        )
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