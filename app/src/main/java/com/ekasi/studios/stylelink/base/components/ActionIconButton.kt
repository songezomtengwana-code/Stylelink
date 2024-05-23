package com.ekasi.studios.stylelink.base.components

import android.util.Size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ActionIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    iconSize: Int = 20
) {
    FilledIconButton(
        onClick,
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(0.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "search_icon",
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .size(iconSize.dp).
            padding(4.dp)
        )
    }
}