package com.ekasi.studios.stylelink.base.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ekasi.studios.stylelink.ui.theme.white100
import com.ekasi.studios.stylelink.ui.theme.StylelinkTheme
import com.ekasi.studios.stylelink.ui.theme.antonFontFamily
import com.ekasi.studios.stylelink.ui.theme.smallSize

@Composable
fun SolidNavbar(imageUrl: String = "") {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(white100)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp, 8.dp)
        ) {
            Text(
                text = "Stylelink.",
                fontFamily = antonFontFamily,
                fontSize = 22.sp
            )
            Card(
                modifier = Modifier
                    .height(smallSize)
                    .width(smallSize)
                    .clickable { TODO("Navigate to profile screen") },
                shape = MaterialTheme.shapes.extraLarge
            ) {

                AsyncImage(
                    model = imageUrl,
                    contentDescription = "user_profile_image",
                    modifier = Modifier.clickable { TODO("") })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SolidNavbarPreview() {
    StylelinkTheme {
        SolidNavbar()
    }
}