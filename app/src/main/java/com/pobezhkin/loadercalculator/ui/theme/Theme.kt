package com.pobezhkin.loadercalculator.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// === ЦВЕТА TELEGRAM ===
val TelegramBlack = Color(0xFF000000)
val TelegramSurface = Color(0xFF1C1C1C)
val TelegramOnSurface = Color(0xFFFFFFFF)
val TelegramOnSurfaceSecondary = Color(0xFFB3B3B3)
val TelegramBlue = Color(0xFF0088CC)
val TelegramError = Color(0xFFFF4F5A)

// Только тёмная тема (как в Telegram)
val TelegramDarkColorScheme = darkColorScheme(
    primary = TelegramBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF006699),
    onPrimaryContainer = Color.White,

    secondary = TelegramBlue,
    onSecondary = Color.White,

    background = TelegramBlack,
    onBackground = TelegramOnSurface,

    surface = TelegramSurface,
    onSurface = TelegramOnSurface,
    onSurfaceVariant = TelegramOnSurfaceSecondary,

    error = TelegramError,
    onError = Color.White
)

@Composable
fun LoaderCalculatorTheme(
    darkTheme: Boolean = true, // Telegram всегда тёмный
    dynamicColor: Boolean = false, // Отключаем Material You
    content: @Composable () -> Unit
) {
    // Telegram всегда в тёмной теме
    MaterialTheme(
        colorScheme = TelegramDarkColorScheme,
        typography = Typography,
        content = content
    )
}