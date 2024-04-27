package com.ekasi.studios.stylelink.base.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ekasi.studios.stylelink.R
import com.ekasi.studios.stylelink.base.common.composables.white100
import com.ekasi.studios.stylelink.ui.theme.StylelinkTheme
import com.ekasi.studios.stylelink.ui.theme.antonFontFamily
import com.ekasi.studios.stylelink.ui.theme.mediumSize
import com.ekasi.studios.stylelink.ui.theme.smallSize

@Composable
fun BlurNavBar(
    onClick: () -> Unit
) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp, 8.dp)
        ) {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    contentDescription = "back_icon",
                    modifier = Modifier.size(500.dp)
                )
            }
            Card(
                modifier = Modifier
                    .height(smallSize)
                    .width(smallSize),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar_local),
                    contentDescription = "stylelink_logo_white",
                    modifier = Modifier
                        .height(mediumSize)
                        .clickable { /* TODO */ },

                    )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BlurNavBarPreview() {
    StylelinkTheme {
        BlurNavBar(onClick = {})
    }
}