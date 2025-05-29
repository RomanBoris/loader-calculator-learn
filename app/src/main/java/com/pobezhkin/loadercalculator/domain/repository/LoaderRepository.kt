package com.pobezhkin.loadercalculator.domain.repository

import com.pobezhkin.loadercalculator.domain.model.LoaderTruckModel
import kotlinx.coroutines.flow.Flow

interface LoaderRepository {

    fun  getAllTrucks(): Flow<List<LoaderTruckModel>>


    suspend fun addTrucks(eo: Int, fz: Int)

    suspend fun deleteTrucks(truck : LoaderTruckModel)

}