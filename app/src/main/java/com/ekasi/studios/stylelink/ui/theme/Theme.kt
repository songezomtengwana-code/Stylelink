package com.ekasi.studios.stylelink.base.common.composables

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.ekasi.studios.stylelink.ui.theme.StylelinkTypography

private val DarkColorScheme = darkColorScheme(
    primary = black80,
    secondary = grey,
    tertiary = white20,
    background = white80,
    surface = white60,
    onPrimary = white80,
    onSecondary = black40,
    onTertiary = white80,
    onBackground = black80,
    onSurface = black60,
)

private val LightColorScheme = lightColorScheme(
    primary = black80,
    secondary = grey,
    tertiary = white20,
    background = white80,
    surface = white60,
    onPrimary = white80,
    onSecondary = black40,
    onTertiary = white80,
    onBackground = black80,
    onSurface = black60,
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