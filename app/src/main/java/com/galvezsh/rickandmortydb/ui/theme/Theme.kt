package com.galvezsh.rickandmortydb.ui.theme

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
    primary = Latte300, // Fondo de items del paging
    onPrimary = LatteTextPrimaryLight, // texto de los items
    secondary = Latte500Light,
    onSecondary = LatteTextSecondaryLight,
    background = Latte200,
    onBackground = Latte300,
    surface = LatteTextSecondaryLight,
    surfaceVariant = RustyOrange,
)

private val DarkColorScheme = darkColorScheme(
    primary = Latte700, // Fondo de items del paging
    onPrimary = LatteTextPrimaryDark, // texto de los items
    secondary = Latte500Dark,
    onSecondary = LatteTextSecondaryDark,
    background = Latte800,
    onBackground = Latte700,
    surface = LatteTextSecondaryDark,
    surfaceVariant = RustyOrange,
)

//// ✅ Light ColorScheme
//val LightColorScheme = lightColorScheme(
//    primary = Latte500Light,
//    onPrimary = Color.White,
//    secondary = Latte300,
//    onSecondary = LatteTextPrimaryLight,
//    background = Latte50,
//    onBackground = LatteTextPrimaryLight,
//    surface = Latte100,
//    onSurface = LatteTextPrimaryLight,
//    surfaceVariant = Latte200,
//    onSurfaceVariant = LatteTextSecondaryLight
//)

//// 🌙 Dark ColorScheme
//val DarkColorScheme = darkColorScheme(
//    primary = Latte600,
//    onPrimary = Color.Black,
//    secondary = Latte700,
//    onSecondary = LatteTextPrimaryDark,
//    background = Latte900,
//    onBackground = LatteTextPrimaryDark,
//    surface = Latte800,
//    onSurface = LatteTextPrimaryDark,
//    surfaceVariant = Latte700,
//    onSurfaceVariant = LatteTextSecondaryDark
//)

@Composable
fun RickAndMortyDBTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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