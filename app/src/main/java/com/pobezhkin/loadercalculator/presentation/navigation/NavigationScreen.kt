package com.pobezhkin.loadercalculator.presentation.navigation

sealed class NavigationScreen(val route: String) {
    object WorkingShift : NavigationScreen("working_shift")
    object History : NavigationScreen("history")
}
