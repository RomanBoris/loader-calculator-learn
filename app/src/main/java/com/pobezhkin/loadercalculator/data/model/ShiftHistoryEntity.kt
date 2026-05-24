package com.pobezhkin.loadercalculator.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shift_history")
data class ShiftHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val savedDate: Long,
    val hoursWorked: Double,
    val totalLoadEo: Int?,
    val totalLoadFzEo: Int?,
    val totalUploadEo: Int?,
    val totalMiniEo: Int?,
    val totalMiniFzEo: Int?,
    val performancePercent: Double
)
