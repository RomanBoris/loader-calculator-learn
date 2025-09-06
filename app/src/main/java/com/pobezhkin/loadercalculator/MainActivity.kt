package com.pobezhkin.loadercalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.pobezhkin.loadercalculator.presentation.screens.ScreenAddCar
import com.pobezhkin.loadercalculator.ui.theme.LoaderCalculatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            LoaderCalculatorTheme {
                ScreenAddCar()
            }
        }
    }
}



