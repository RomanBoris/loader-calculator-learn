package com.pobezhkin.loadercalculator.domain.repository

import com.pobezhkin.loadercalculator.domain.model.LoaderTruckModel
import com.pobezhkin.loadercalculator.domain.model.MiniTruckModel
import com.pobezhkin.loadercalculator.domain.model.UploadTruckModel
import kotlinx.coroutines.flow.Flow

interface LoaderRepository {

    fun  getAllTrucks(): Flow<List<LoaderTruckModel>>

    suspend fun addTrucks(eo: Int, fz: Int)

    suspend fun deleteTrucks(truck : LoaderTruckModel)

    suspend fun updateTrucks(truck : LoaderTruckModel)


    fun getAllUploads(): Flow<List<UploadTruckModel>>

    suspend fun addUploadsTruck(uploadEo: Int)

    suspend fun deleteUploadsTruck(uploadTruck: UploadTruckModel)

    suspend fun updatesUploadsTruck(uploadTruck: UploadTruckModel)


    fun  getAllMiniTrucks(): Flow<List<MiniTruckModel>>

    suspend fun addMiniTrucks(mini_eo: Int, mini_fz_eo: Int)

    suspend fun deleteMiniTrucks(miniTruck : MiniTruckModel)

    suspend fun updateMiniTrucks(miniTruck : MiniTruckModel)

}