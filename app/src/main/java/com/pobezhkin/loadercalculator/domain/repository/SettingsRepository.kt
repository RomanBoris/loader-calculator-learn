package com.pobezhkin.loadercalculator.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getHoursWorked(): Flow<Double>
    suspend fun saveHoursWorked(hours: Double)
}
