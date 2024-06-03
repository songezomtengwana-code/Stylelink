package com.ekasi.studios.stylelink.base.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.ekasi.studios.stylelink.ui.theme.smallSize

@Composable
fun ActionButton(
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    title: String
) {
    Button(
        enabled = isEnabled,
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(smallSize),
    ) {
        Text(title, fontWeight = FontWeight.Bold)
    }
}