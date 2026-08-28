package com.androidcicdlab.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PurpleLight,
    onPrimary = PurpleDeep,
    primaryContainer = PurpleDark,
    onPrimaryContainer = PurpleLight,
    secondary = PurpleGrey80,
    tertiary = PurpleLight,
    background = PurpleDeep,
    surface = PurpleDeep,
    surfaceContainerHigh = PurpleSurfaceDark,
)

private val LightColorScheme = lightColorScheme(
    primary = PurplePrimary,
    onPrimary = Color.White,
    primaryContainer = PurpleContainerLight,
    onPrimaryContainer = PurpleDark,
    secondary = PurpleGrey40,
    tertiary = PurpleDark,
    background = PurpleMist,
    surface = PurpleSurfaceLight,
    surfaceContainerHigh = Color.White,
)

/**
 * Material 3 theme for AndroidCICDLab.
 *
 * Dynamic (system) color is off by default so the `#6200EE` brand stays consistent
 * across devices. The purple screen gradient is painted by the home screen, not here.
 */
@Composable
fun AndroidCICDLabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content,
    )
}

/**
 * Vertical purple wash that sits behind home content.
 * Light theme: `#6200EE` fades into [PurpleMist]. Dark theme: [PurpleDark] into [PurpleDeep].
 */
@Composable
fun labScreenGradient(darkTheme: Boolean = isSystemInDarkTheme()): Brush {
    return if (darkTheme) {
        Brush.verticalGradient(
            colors = listOf(PurpleDark, PurplePrimary.copy(alpha = 0.55f), PurpleDeep),
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                PurplePrimary.copy(alpha = 0.32f),
                PurpleLight.copy(alpha = 0.22f),
                PurpleMist,
                PurpleSurfaceLight,
            ),
        )
    }
}
