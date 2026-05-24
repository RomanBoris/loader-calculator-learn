package com.pobezhkin.loadercalculator.domain.repository

import com.pobezhkin.loadercalculator.domain.model.ShiftHistoryModel
import kotlinx.coroutines.flow.Flow

interface ShiftHistoryRepository {

    fun getAllShiftHistory(): Flow<List<ShiftHistoryModel>>

    suspend fun insertShiftHistory(shiftHistoryModel: ShiftHistoryModel)

    suspend fun updateShiftHistory(shiftHistoryModel: ShiftHistoryModel)

    suspend fun deleteShiftHistory(shiftHistoryModel: ShiftHistoryModel)
}
