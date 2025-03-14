package com.bolaware.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light Theme Colors
val LightColorPalette = lightColorScheme(
    primary = Green500,
    onPrimary = Color.White,
    secondary = Teal200,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = Color.Black
)

// Dark Theme Colors
val DarkColorPalette = darkColorScheme(
    primary = Green200,
    onPrimary = Color.Black,
    secondary = Teal200,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = Color.White
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}