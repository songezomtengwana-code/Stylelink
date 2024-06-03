package com.ekasi.studios.stylelink.base.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ekasi.studios.stylelink.ui.theme.StylelinkTheme
import com.ekasi.studios.stylelink.ui.theme.tinySize

@Composable
fun Header(
    onSearchClicked: () -> Unit,
    onSearchLocationClicked: () -> Unit,
) {
    var searchText = ""

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(tinySize, tinySize / 2),
        horizontalArrangement = Arrangement.spacedBy(
            tinySize / 4
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
            ),
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .weight(1f),
            singleLine = true,
            shape = MaterialTheme.shapes.extraLarge,
            placeholder = { Text("Search") }
        )
        OutlinedIconButton(
            onClick = onSearchClicked,
            modifier = Modifier
                .height(51.dp)
                .width(51.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "navigation_back",
                modifier = Modifier
                    .height(27.5.dp)
                    .width(27.5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    StylelinkTheme {
        Header(
            onSearchClicked = {},
            onSearchLocationClicked = {}
        )
    }
}
