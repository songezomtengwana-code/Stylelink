package com.ekasi.studios.stylelink.base.components.StorePreview

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Directions
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ekasi.studios.stylelink.data.model.Marker
import com.ekasi.studios.stylelink.ui.theme.tinySize
import com.ekasi.studios.stylelink.utils.dateUtils.isOpen
import com.ekasi.studios.stylelink.utils.services.stores.models.Store


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StorePreview(
    storeId: String,
    storePreviewViewModel: StorePreviewViewModel,
    marker: Marker?,
    bookServiceHandler: () -> Unit = {},
    directionsHandler: () -> Unit = {},
    bookmarkHandler: () -> Unit = {},
    shareHandler: () -> Unit = {},
) {
    val cardHeight = LocalConfiguration.current.screenHeightDp.dp / 2
    val state = storePreviewViewModel.state.collectAsState()
    LaunchedEffect(storeId) {
        if (marker != null) {
            storePreviewViewModel.fetchStoreProfile(storeId)
        } else {
            Log.d("fetchStoreProfile", "Marker is null")
        }
    }

    when (state.value) {
        is StorePreviewState.Success -> {
            val store = (state.value as StorePreviewState.Success).store
            StorePreviewBody(
                store = store,
                marker = marker!!,
                bookmarkHandler = bookmarkHandler,
                bookServiceHandler = bookServiceHandler,
                directionsHandler = directionsHandler,
                shareHandler = shareHandler
            )
        }

        else -> {
            StorePreviewSkeleton()
        }
    }
}

@Composable
fun StorePreviewSkeleton() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val background = Color.LightGray.copy(alpha = alpha)

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth(0.6f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(background)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth(0.2f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(background)
                )
            }
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray.copy(alpha = alpha))
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth(0.3f)
                .clip(RoundedCornerShape(4.dp))
                .background(background)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(4.dp))
                .background(background)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(background)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StorePreviewBody(
    store: Store, marker: Marker, bookServiceHandler: () -> Unit = {},
    directionsHandler: () -> Unit = {},
    bookmarkHandler: () -> Unit = {},
    shareHandler: () -> Unit = {},
) {
    val background = Color.Transparent
    var isFavourite = false
    val cardWidth = LocalConfiguration.current.screenWidthDp.dp - 32.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Column {
                Text(
                    text = store.name.capitalize(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val starCount = 5
                    for (i in 1..starCount) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = "clock_indicator_icon",
                            modifier = Modifier.height(tinySize),
                            tint = Color.LightGray,
//                            tint = Color(0xFFFFAB00),
                        )
                    }
                    Text(text = store.rating.toString(), fontWeight = FontWeight.Bold)
                }
            }

            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape)
            ) {
                AsyncImage(
                    model = store.profileImage,
                    contentDescription = "profile_image",
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val openingState = isOpen(store.businessHours!!)
            val next =
                if (openingState == "open") "Closes ${store.businessHours[1]}" else "Opens ${store.businessHours[0]}"
            Text(
                text = openingState.capitalize(),
                color = if (openingState == "open") Color(0xFF008000) else Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(text = "•", color = Color.DarkGray)
            Text(
                text = next,
                color = Color.DarkGray,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.width(cardWidth)
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "clock_indicator_icon",
                tint = Color.DarkGray
            )
            Text(
                text = marker.markerAddress,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Clip,
                color = Color.DarkGray,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = bookServiceHandler,
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .weight(1f),
            ) {
                Text(text = "Book Services")
                Spacer(modifier = Modifier.width(4.dp))
            }
            Row(
                modifier = Modifier
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Outlined.Directions,
                        contentDescription = "share_icon",
                        tint = Color.DarkGray
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Outlined.Bookmark,
                        contentDescription = "favorite_icon",
                        tint = Color.DarkGray
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.Share,
                        contentDescription = "share_icon",
                        tint = Color.DarkGray
                    )
                }
            }
        }
    }
}