package com.pobezhkin.loadercalculator.presentation.screens.HistoryScreen

import com.pobezhkin.loadercalculator.domain.model.ShiftHistoryModel

data class ShiftHistoryUiState(
    val shiftHistory: List<ShiftHistoryModel> = emptyList()
)
