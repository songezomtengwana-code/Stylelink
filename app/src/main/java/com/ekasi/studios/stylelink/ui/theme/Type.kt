package com.ekasi.studios.stylelink.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.ekasi.studios.stylelink.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val anton = GoogleFont("Anton");
val poppins = GoogleFont("Poppins");

val antonFontFamily = FontFamily(
    Font(googleFont = anton, fontProvider = provider)
)

val poppinsFontFamily = FontFamily(
    Font(googleFont = poppins, fontProvider = provider)
)

private val defaultTypography = Typography()

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val StylelinkTypography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = antonFontFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = antonFontFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = poppinsFontFamily),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = antonFontFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = antonFontFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = poppinsFontFamily),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = poppinsFontFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = poppinsFontFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = poppinsFontFamily),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = poppinsFontFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = poppinsFontFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = poppinsFontFamily),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = poppinsFontFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = poppinsFontFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = poppinsFontFamily)
)
