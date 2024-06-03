package com.ekasi.studios.stylelink.base.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ekasi.studios.stylelink.base.components.ActionIconButton
import com.ekasi.studios.stylelink.ui.theme.tinySize
import com.ekasi.studios.stylelink.utils.services.stores.models.Store

@Composable
fun StoreCard(
    store: Store,
    onClick: () -> Unit
) {
    val cardWidth = LocalConfiguration.current.screenWidthDp.dp - 32.dp
    var isFavourite by rememberSaveable { mutableStateOf(false) }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .padding(0.dp, tinySize)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() },
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                AsyncImage(
                    model = store.profileImage,
                    contentDescription = store.name,
                    modifier = Modifier
                        .height(LocalConfiguration.current.screenHeightDp.dp / 3.75f)
                        .width(LocalConfiguration.current.screenWidthDp.dp - 32.dp),
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier.padding(8.dp),
                ) {
                    ActionIconButton(
                        onClick = { isFavourite = !isFavourite },
                        icon = if (isFavourite) {
                            Icons.Rounded.Bookmark
                        } else {
                            Icons.Rounded.BookmarkBorder
                        },
                        backgroundColor = Color(0x1D000000),
                        textColor = MaterialTheme.colorScheme.background,
                        shape = CircleShape,
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .width(cardWidth)
                    .padding(0.dp, 16.dp, 8.dp)
            )
            {
                Text(
                    text = store.name.capitalize(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = "clock_indicator_icon",
                        modifier = Modifier.height(tinySize),
                        tint = Color(0xFFFFAB00)
                    )
                    Text(text = store.rating.toString(), fontWeight = FontWeight.Bold)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.AccessTime,
                    contentDescription = "clock_indicator_icon",
                    tint = Color.Gray
                )
                Text(
                    text = "${store.businessHours!![0]} - ${store.businessHours[1]}",
                    color = Color.Gray,
                    style= MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.width(cardWidth)
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "clock_indicator_icon",
                    tint = Color.Gray
                )
                Text(
                    text = store.address.toString(),
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Clip,
                    color = Color.Gray,
                    style= MaterialTheme.typography.bodyLarge
                )
            }
        }

    }
}

