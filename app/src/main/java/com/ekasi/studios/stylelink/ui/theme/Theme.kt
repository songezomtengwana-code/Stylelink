package com.ekasi.studios.stylelink.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = black80,
    secondary = grey,
    tertiary = white20,
    background = white100,
    surface = white100,
    onPrimary = white100,
    onSecondary = black40,
    onTertiary = white100,
    onBackground = black80,
    onSurface = black60,
)

private val LightColorScheme = lightColorScheme(
    primary = black80,
    secondary = grey,
    tertiary = white20,
    background = white100,
    surface = white100,
    onPrimary = white100,
    onSecondary = black40,
    onTertiary = white100,
    onBackground = black80,
    onSurface = black60,
    onPrimaryContainer = white100
    )

@Composable
fun StylelinkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = StylelinkTypography,
        content = content
    )
}