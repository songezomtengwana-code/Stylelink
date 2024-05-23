package com.ekasi.studios.stylelink.base.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Notifications
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
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
            ) { onClick() }
    ) {
        Column(
        ) {
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                AsyncImage(
                    model = store.profileImage,
                    contentDescription = store.name,
                    modifier = Modifier
                        .height(LocalConfiguration.current.screenHeightDp.dp / 5)
                        .width(LocalConfiguration.current.screenHeightDp.dp / 5),
                    contentScale = ContentScale.Crop
                )
                ActionIconButton(
                    onClick = { isFavourite = !isFavourite },
                    icon = if (isFavourite) {
                        Icons.Rounded.Favorite
                    } else {
                        Icons.Rounded.FavoriteBorder
                    },
                    backgroundColor = MaterialTheme.colorScheme.background,
                    textColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                )
            }

            Column(
                modifier = Modifier
                    .width(LocalConfiguration.current.screenHeightDp.dp / 5)
                    .padding(0.dp, 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                when (store.isActive) {
                    true -> {
                        Card(
                            modifier = Modifier
                                .padding(0.dp, 12.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Green
                            )
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp, 2.dp),
                                text = "online",
                                color = Color.Black,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
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
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Notifications,
                        contentDescription = "clock_indicator_icon",
                        modifier = Modifier.height(tinySize)
                    )
                    Text(
                        text = "${store.businessHours!![0]} - ${store.businessHours[1]}",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = store.address.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2
                )
            }
        }
    }
}