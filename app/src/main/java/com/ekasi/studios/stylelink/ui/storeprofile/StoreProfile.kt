package com.ekasi.studios.stylelink.ui.storeprofile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ekasi.studios.stylelink.base.composable.ProfileTitle
import com.ekasi.studios.stylelink.base.composable.StatBar
import com.ekasi.studios.stylelink.ui.theme.mediumSize
import com.ekasi.studios.stylelink.ui.theme.tinySize
import com.ekasi.studios.stylelink.utils.services.stores.models.Store

@Composable
fun StoreProfile(
    storeProfileViewModel: StoreProfileViewModel,
    storeId: String?
) {
    val id = storeId ?: return
    val state = storeProfileViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        /**
         * Fetches A Single Store Profile
         *
         * @param storeId: String
         */
        storeProfileViewModel.fetchStoreProfile(id)
    }

    BackHandler(
        enabled = true,
        onBack = { storeProfileViewModel.clearState() }
    )

    when (state.value) {
        is StoreProfileState.Loading -> {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .width(mediumSize)
                )
            }
        }

        is StoreProfileState.Success -> {
            val profile = (state.value as StoreProfileState.Success).store
            StoreProfileComponent(store = profile)
        }

        is StoreProfileState.Error -> {
            val message = (state.value as StoreProfileState.Error).message
            Text(text = message)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreProfileComponent(store: Store) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        store.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { TODO("Integrate nav controller for popBackStack()") }) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = "back_Button"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(tinySize, paddingValues.calculateTopPadding()),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .height(mediumSize)
                        .width(mediumSize)
                        .clip(RoundedCornerShape(100f)),
                ) {
                    AsyncImage(
                        model = store.profileImage,
                        contentDescription = "store_profile_image",
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .height(mediumSize)
                            .width(mediumSize),
                        contentScale = ContentScale.FillHeight
                    )
                }
                StatBar(value = "1,2K", title = "Reviews")
                StatBar(value = "10", title = "Services")
            }
            Text(
                text = store.run { name.capitalize() },
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )
            val description =
                "Lorem ipsum dolor sit amet. Ut ducimus omnis vel reiciendis ducimus et veniam consequatur."
            Text(
                text = description,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(0.dp, 4.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.width(IntrinsicSize.Max)
                ) {

                    Text(text = "Chat a stylist")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Rounded.Send,
                        contentDescription = "chat_with_stylist",
                        modifier = Modifier.size(tinySize)
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "favorite_icon"
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.Share, contentDescription = "share_icon")
                }
            }
            ProfileTitle(title = "Available Services")
        }
    }
}