package com.pobezhkin.loadercalculator.presentation.screens.HistoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.pobezhkin.loadercalculator.presentation.screens.HistoryScreen.components.ShiftHistoryCard
import com.pobezhkin.loadercalculator.presentation.screens.mainScreen.BluePalette

@Composable
fun HistoryShiftScreen(
    viewModel: ShiftHistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BluePalette.Background)
    ) {
        if (uiState.shiftHistory.isEmpty()) {
            Text(
                text = "История пуста",
                modifier = Modifier.align(Alignment.Center),
                color = BluePalette.TextSecondary
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(uiState.shiftHistory, key = { it.id }) { shift ->
                    ShiftHistoryCard(
                        shift = shift,
                        onDelete = { viewModel.deleteShiftHistory(shift) }
                    )
                }
            }
        }
    }
}
