package com.pobezhkin.loadercalculator.data.workshift.repository

import com.pobezhkin.loadercalculator.data.workshift.LoadedTruck
import kotlinx.coroutines.flow.Flow

interface LoaderRepository {
    suspend fun addTrucks(truck : LoadedTruck)
    suspend fun removeTrucks(truck : LoadedTruck)
      fun  getAllTrucksStream(): Flow<List<LoadedTruck>>
}