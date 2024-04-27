package com.ekasi.studios.stylelink.base.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ekasi.studios.stylelink.base.common.composables.black20
import com.ekasi.studios.stylelink.base.components.ActionButton
import com.ekasi.studios.stylelink.ui.theme.StylelinkTheme
import com.ekasi.studios.stylelink.ui.theme.tinySize


@Composable
fun Empty(
    text: String,
    suggestion: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, tinySize)
            .height(IntrinsicSize.Max),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = text, style = MaterialTheme.typography.titleMedium,  textAlign = TextAlign.Center)
                Text(text = suggestion, color = black20, style = MaterialTheme.typography.labelLarge, textAlign = TextAlign.Center)
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                ActionButton(onClick = { /*TODO*/ }, title = "Browse Stores")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyPreview() {
    StylelinkTheme {
        Empty(
            text = "You dont have any favorites yet :(",
            suggestion = "Browse available stores that you might like",
            onClick = {}
        )
    }
}