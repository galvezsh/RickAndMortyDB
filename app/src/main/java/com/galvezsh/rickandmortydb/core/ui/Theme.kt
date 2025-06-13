package com.galvezsh.rickandmortydb.core.ui

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = Latte300, // background color for paging items
    onPrimary = LatteTextPrimaryLight, // text color for paging items
    primaryContainer = PortalGreen,
    onPrimaryContainer = DeadGreen,
    secondary = Latte500Light,
    onSecondary = LatteTextSecondaryLight,
    background = Latte200,
    onBackground = Latte300,
    surface = LatteTextSecondaryLight,
    surfaceVariant = RustyOrange
)

private val DarkColorScheme = darkColorScheme(
    primary = Latte700,
    onPrimary = LatteTextPrimaryDark,
    primaryContainer = PortalGreen,
    onPrimaryContainer = DeadGreen,
    secondary = Latte500Dark,
    onSecondary = LatteTextSecondaryDark,
    background = Latte800,
    onBackground = Latte700,
    surface = LatteTextSecondaryDark,
    surfaceVariant = RustyOrange
)

@Composable
fun RickAndMortyDBTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}