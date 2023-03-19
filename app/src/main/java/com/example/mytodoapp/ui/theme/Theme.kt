package com.example.mytodoapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

val DarkColorPalette = darkColors(
    primary = PrimaryDark,
    secondary = SecondaryDark,
    primaryVariant = ListItemDark,
    secondaryVariant = InactiveDark,
    onSecondary = PlaceholderDark
)

val LightColorPalette = lightColors(
    primary = PrimaryLight,
    secondary = SecondaryLight,
    primaryVariant = ListItemLight,
    secondaryVariant = InactiveLight,
    onSecondary = PlaceholderLight

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun MyTodoAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}