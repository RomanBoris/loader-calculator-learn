package com.pobezhkin.loadercalculator.domain

import com.pobezhkin.loadercalculator.data.workshift.LoadedTruck
import com.pobezhkin.loadercalculator.data.workshift.LoadedTruckDao
import com.pobezhkin.loadercalculator.data.workshift.repository.LoaderRepository
import kotlinx.coroutines.flow.Flow

class LoaderRepositoryImpl(
    private val truckDao: LoadedTruckDao
): LoaderRepository {

    override suspend fun addTrucks(truck : LoadedTruck) {
        truckDao.insert(truck)
    }
    override suspend fun removeTrucks(truck : LoadedTruck) {
        truckDao.delete(truck)
    }

    override fun getAllTrucksStream() : Flow<List<LoadedTruck>> {
      return  truckDao.getAllTrucks()
    }


}