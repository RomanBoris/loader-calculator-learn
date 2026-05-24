package com.pobezhkin.loadercalculator.domain.usecase

import com.pobezhkin.loadercalculator.domain.model.ShiftHistoryModel
import com.pobezhkin.loadercalculator.domain.repository.ShiftHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShiftHistoryUseCase @Inject constructor(
    private val repository: ShiftHistoryRepository
) {
    operator fun invoke(): Flow<List<ShiftHistoryModel>> =
        repository.getAllShiftHistory()
}

class InsertShiftHistoryUseCase @Inject constructor(
    private val repository: ShiftHistoryRepository
) {
    suspend operator fun invoke(shiftHistoryModel: ShiftHistoryModel) =
        repository.insertShiftHistory(shiftHistoryModel)
}

class UpdateShiftHistoryUseCase @Inject constructor(
    private val repository: ShiftHistoryRepository
) {
    suspend operator fun invoke(shiftHistoryModel: ShiftHistoryModel) =
        repository.updateShiftHistory(shiftHistoryModel)
}

class DeleteShiftHistoryUseCase @Inject constructor(
    private val repository: ShiftHistoryRepository
) {
    suspend operator fun invoke(shiftHistoryModel: ShiftHistoryModel) =
        repository.deleteShiftHistory(shiftHistoryModel)
}
