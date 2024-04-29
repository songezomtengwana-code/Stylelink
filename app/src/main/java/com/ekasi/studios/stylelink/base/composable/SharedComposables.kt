package com.ekasi.studios.stylelink.base.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ekasi.studios.stylelink.base.common.composables.black20
import com.ekasi.studios.stylelink.ui.theme.StylelinkTheme

@Composable
fun SectionTitle(
    title: String,
    count: Int,
    moreAction: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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

