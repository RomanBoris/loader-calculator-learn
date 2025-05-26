package com.pobezhkin.loadercalculator.data.workshift.repository

import com.pobezhkin.loadercalculator.data.workshift.LoadedTruck
import com.pobezhkin.loadercalculator.data.workshift.LoadedTruckDao
import kotlinx.coroutines.flow.Flow

class LoaderRepository(private val loadedTruckDao : LoadedTruckDao) {

    val allTrucks : Flow<List<LoadedTruck>> = loadedTruckDao.getAllTrucks()


    suspend fun addTrucks(eo: Int, fz: Int) {
        loadedTruckDao.insert(LoadedTruck( processingUnit = eo, processingUnitFreeze = fz))
    }

    suspend fun deleteTrucks(truck : LoadedTruck) {
        loadedTruckDao.delete(truck)
    }

}