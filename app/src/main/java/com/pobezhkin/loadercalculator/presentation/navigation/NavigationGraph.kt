package com.pobezhkin.loadercalculator.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pobezhkin.loadercalculator.presentation.screens.HistoryScreen.HistoryShiftScreen
import com.pobezhkin.loadercalculator.presentation.screens.mainScreen.ScreenAddCar

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.WorkingShift.route
    ) {
        composable(NavigationScreen.WorkingShift.route) {
            ScreenAddCar()
        }
        composable(NavigationScreen.History.route) {
            HistoryShiftScreen()
        }
    }
}
