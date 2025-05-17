package com.pobezhkin.loadercalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pobezhkin.loadercalculator.presentation.screens.ScreenAddCar
import com.pobezhkin.loadercalculator.ui.theme.LoaderCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoaderCalculatorTheme {
                ScreenAddCar()
            }
        }
    }
}



