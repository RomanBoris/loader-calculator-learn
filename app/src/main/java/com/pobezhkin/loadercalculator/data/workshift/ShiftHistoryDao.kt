package com.pobezhkin.loadercalculator.data.workshift

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pobezhkin.loadercalculator.data.model.ShiftHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShiftHistoryDao {

    @Query("SELECT * FROM shift_history ORDER BY savedDate DESC")
    fun getAllShiftHistory(): Flow<List<ShiftHistoryEntity>>

    @Insert
    suspend fun insertShiftHistory(shiftHistoryEntity: ShiftHistoryEntity)

    @Update
    suspend fun updateShiftHistory(shiftHistoryEntity: ShiftHistoryEntity)

    @Delete
    suspend fun deleteShiftHistory(shiftHistoryEntity: ShiftHistoryEntity)
}
