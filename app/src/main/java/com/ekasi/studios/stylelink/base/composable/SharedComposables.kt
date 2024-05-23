package com.ekasi.studios.stylelink.base.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ekasi.studios.stylelink.ui.theme.black20
import com.ekasi.studios.stylelink.ui.theme.StylelinkTheme
import com.ekasi.studios.stylelink.ui.theme.mediumSize
import com.ekasi.studios.stylelink.ui.theme.smallSize
import com.ekasi.studios.stylelink.ui.theme.tinySize


@Composable
fun SectionTitle(
    title: String,
    count: Int,
    moreAction: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "$title ($count)",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Black,
            modifier = Modifier
                .height(20.dp)
        )
        Text(
            text = "More",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = black20,
            modifier = Modifier
                .clickable(enabled = true) {}
        )

    }
}

@Preview(showBackground = true)
@Composable
fun SectionTitlePreview() {
    StylelinkTheme {
        SectionTitle(
            title = "Some Random Ass Title",
            count = 30,
            moreAction = {}
        )
    }
}

@Composable
fun StatBar(value: String, title: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(4.dp, smallSize),
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(text = title, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun ProfileTitle(title: String) {
    Column {
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,

            )
    }
}

@Composable
fun CarouselLoader(text: String) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .height(LocalConfiguration.current.screenWidthDp.dp / 2)
            .padding(0.dp, 8.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = text)
        LinearProgressIndicator(
            modifier = Modifier
                .width(mediumSize)
        )
    }
}

@Composable
fun CarouselError(message: String, handler: () -> Unit) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .height(LocalConfiguration.current.screenWidthDp.dp / 2)
            .padding(0.dp, 8.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message)
        Text(
            text = "Click to refresh",
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    handler
                }
        )
    }
}

@Composable
fun CarouselEmpty(text: String, suggestion: String) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .height(LocalConfiguration.current.screenWidthDp.dp / 2)
            .padding(0.dp, 8.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.titleMedium)
        Text(
            text = suggestion,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun HomeSearchButton() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(smallSize*2)
            .padding(0.dp, tinySize)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {  },
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(tinySize,0.dp)
        ) {

            Text(
                text = "Find Salon",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                lineHeight = 100.sp
            )
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "search_icon",
                modifier = Modifier
                    .height(smallSize)
                    .width(smallSize),
                tint = Color.DarkGray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredTopBar(
    title: String,
    backIconClick: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Black
            )
        },
        navigationIcon = {
            IconButton(onClick = backIconClick) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    contentDescription = "back_Button"
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}