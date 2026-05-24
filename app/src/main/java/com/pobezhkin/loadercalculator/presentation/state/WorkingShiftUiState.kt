package com.pobezhkin.loadercalculator.presentation.state

import com.pobezhkin.loadercalculator.domain.model.LoaderTruckModel
import com.pobezhkin.loadercalculator.domain.model.MiniTruckModel
import com.pobezhkin.loadercalculator.domain.model.UploadTruckModel

data class WorkingShiftUiState(
    val trucks: List<LoaderTruckModel> = emptyList(),
    val uploads: List<UploadTruckModel> = emptyList(),
    val miniTrucks: List<MiniTruckModel> = emptyList(),
    val performancePercent: Double = 0.0
)
