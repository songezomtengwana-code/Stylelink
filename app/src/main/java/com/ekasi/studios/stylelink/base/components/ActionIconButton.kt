package com.ekasi.studios.stylelink.base.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ActionIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    iconSize: Int = 27,
    textColor: Color = MaterialTheme.colorScheme.background,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = MaterialTheme.shapes.small
) {
    FilledIconButton(
        onClick,
        shape = shape,
        modifier = Modifier
            .padding(0.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "action_icon_button",
            tint = textColor,
            modifier = Modifier
                .size(iconSize.dp)
                .padding(4.dp)
        )
    }
}