package com.pobezhkin.loadercalculator.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

// Основные цвета из палитры (HEX -> Color)
val BluePrimary = Color(0xFF4BB6F4)
val BlueSecondary = Color(0xFF1F9CE4)
val BlueTertiary = Color(0xFF3E60C1)
val BlueAccent = Color(0xFF5983FC)

// Цвета для светлой темы
val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = Color.White,
    primaryContainer = BlueSecondary,
    onPrimaryContainer = Color.White,

    secondary = BlueTertiary,
    onSecondary = Color.White,
    secondaryContainer = BlueAccent,
    onSecondaryContainer = Color.White,

    tertiary = Pink40,
    onTertiary = Color.White,

    background = Color(0xFFFAFDFF),
    onBackground = Color(0xFF001F29),

    surface = Color(0xFFFAFDFF),
    onSurface = Color(0xFF001F29),

    surfaceVariant = Color(0xFFDCE3E9),
    onSurfaceVariant = Color(0xFF40484D)
)

// Цвета для тёмной темы
val DarkColorScheme = darkColorScheme(
    primary = BluePrimary,
    onPrimary = Color.Black,
    primaryContainer = BlueSecondary,
    onPrimaryContainer = Color.White,

    secondary = BlueTertiary,
    onSecondary = Color.White,
    secondaryContainer = BlueAccent,
    onSecondaryContainer = Color.White,

    tertiary = Pink80,
    onTertiary = Color.Black,

    background = Color(0xFF001F29),
    onBackground = Color(0xFFBFE9FF),

    surface = Color(0xFF001F29),
    onSurface = Color(0xFFBFE9FF),

    surfaceVariant = Color(0xFF40484D),
    onSurfaceVariant = Color(0xFFC0C7CD)
)

@Composable
fun LoaderCalculatorTheme(
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

/*
@Composable
fun LoaderCalculatorTheme(
    darkTheme : Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor : Boolean = true,
    content : @Composable () -> Unit
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
}*/