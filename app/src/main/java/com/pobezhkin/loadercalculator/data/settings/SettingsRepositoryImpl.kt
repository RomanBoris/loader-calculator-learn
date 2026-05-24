package com.pobezhkin.loadercalculator.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import com.pobezhkin.loadercalculator.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    private val hoursWorkedKey = doublePreferencesKey("hours_worked")

    override fun getHoursWorked(): Flow<Double> =
        dataStore.data.map { prefs -> prefs[hoursWorkedKey] ?: 11.0 }

    override suspend fun saveHoursWorked(hours: Double) {
        dataStore.edit { prefs -> prefs[hoursWorkedKey] = hours }
    }
}
