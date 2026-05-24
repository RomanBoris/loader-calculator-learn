package com.pobezhkin.loadercalculator.data.workshift.repository

import com.pobezhkin.loadercalculator.data.workshift.ShiftHistoryDao
import com.pobezhkin.loadercalculator.domain.model.ShiftHistoryModel
import com.pobezhkin.loadercalculator.domain.repository.ShiftHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShiftHistoryRepositoryImpl @Inject constructor(
    private val shiftHistoryDao: ShiftHistoryDao
) : ShiftHistoryRepository {

    override fun getAllShiftHistory(): Flow<List<ShiftHistoryModel>> =
        shiftHistoryDao.getAllShiftHistory().map { list ->
            list.map { it.toShiftHistoryModel() }
        }

    override suspend fun insertShiftHistory(shiftHistoryModel: ShiftHistoryModel) {
        shiftHistoryDao.insertShiftHistory(shiftHistoryModel.toShiftHistoryEntity())
    }

    override suspend fun updateShiftHistory(shiftHistoryModel: ShiftHistoryModel) {
        shiftHistoryDao.updateShiftHistory(shiftHistoryModel.toShiftHistoryEntity())
    }

    override suspend fun deleteShiftHistory(shiftHistoryModel: ShiftHistoryModel) {
        shiftHistoryDao.deleteShiftHistory(shiftHistoryModel.toShiftHistoryEntity())
    }
}
