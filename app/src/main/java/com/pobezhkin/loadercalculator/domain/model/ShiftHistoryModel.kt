package com.pobezhkin.loadercalculator.domain.model

data class ShiftHistoryModel(
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
