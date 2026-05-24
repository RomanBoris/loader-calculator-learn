package com.pobezhkin.loadercalculator.domain.usecase

import com.pobezhkin.loadercalculator.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHoursWorkedUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<Double> = repository.getHoursWorked()
}

class SaveHoursWorkedUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(hours: Double) = repository.saveHoursWorked(hours)
}
