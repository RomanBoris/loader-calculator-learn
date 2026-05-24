package com.pobezhkin.loadercalculator.presentation.screens.HistoryScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pobezhkin.loadercalculator.domain.model.ShiftHistoryModel
import com.pobezhkin.loadercalculator.domain.usecase.DeleteShiftHistoryUseCase
import com.pobezhkin.loadercalculator.domain.usecase.GetShiftHistoryUseCase
import com.pobezhkin.loadercalculator.domain.usecase.InsertShiftHistoryUseCase
import com.pobezhkin.loadercalculator.domain.usecase.UpdateShiftHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShiftHistoryViewModel @Inject constructor(
    private val getShiftHistoryUseCase: GetShiftHistoryUseCase,
    private val insertShiftHistoryUseCase: InsertShiftHistoryUseCase,
    private val updateShiftHistoryUseCase: UpdateShiftHistoryUseCase,
    private val deleteShiftHistoryUseCase: DeleteShiftHistoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShiftHistoryUiState())
    val uiState: StateFlow<ShiftHistoryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getShiftHistoryUseCase().collect { history ->
                _uiState.value = ShiftHistoryUiState(shiftHistory = history)
            }
        }
    }

    fun insertShiftHistory(shiftHistoryModel: ShiftHistoryModel) {
        viewModelScope.launch { insertShiftHistoryUseCase(shiftHistoryModel) }
    }

    fun updateShiftHistory(shiftHistoryModel: ShiftHistoryModel) {
        viewModelScope.launch { updateShiftHistoryUseCase(shiftHistoryModel) }
    }

    fun deleteShiftHistory(shiftHistoryModel: ShiftHistoryModel) {
        viewModelScope.launch { deleteShiftHistoryUseCase(shiftHistoryModel) }
    }
}
