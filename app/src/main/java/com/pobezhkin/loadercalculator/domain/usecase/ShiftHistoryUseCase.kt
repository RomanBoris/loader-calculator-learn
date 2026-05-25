package com.pobezhkin.loadercalculator.domain.usecase

import com.pobezhkin.loadercalculator.domain.model.LoaderTruckModel
import com.pobezhkin.loadercalculator.domain.model.MiniTruckModel
import com.pobezhkin.loadercalculator.domain.model.ShiftHistoryModel
import com.pobezhkin.loadercalculator.domain.model.UploadTruckModel
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

class SaveShiftHistoryUseCase @Inject constructor(
    private val repository: ShiftHistoryRepository
) {
    suspend operator fun invoke(
        trucks: List<LoaderTruckModel>,
        uploads: List<UploadTruckModel>,
        miniTrucks: List<MiniTruckModel>,
        hoursWorked: Double
    ) = repository.insertShiftHistory(
        ShiftHistoryModel(
            savedDate = System.currentTimeMillis(),
            hoursWorked = hoursWorked,
            totalLoadEo = trucks.sumOf { it.h_unit }.takeIf { it > 0 },
            totalLoadFzEo = trucks.sumOf { it.fz_h_unit }.takeIf { it > 0 },
            totalUploadEo = uploads.sumOf { it.upload }.takeIf { it > 0 },
            totalMiniEo = miniTrucks.sumOf { it.mini_eo }.takeIf { it > 0 },
            totalMiniFzEo = miniTrucks.sumOf { it.mini_fz_eo }.takeIf { it > 0 }
        )
    )
}
