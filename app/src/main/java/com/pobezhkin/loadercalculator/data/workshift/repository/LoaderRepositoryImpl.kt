package com.pobezhkin.loadercalculator.data.workshift.repository

import com.pobezhkin.loadercalculator.data.model.LoaderTruckEntity
import com.pobezhkin.loadercalculator.data.model.MiniTruckEntity
import com.pobezhkin.loadercalculator.data.model.UploadTruckEntity
import com.pobezhkin.loadercalculator.data.workshift.LoadedTruckDao
import com.pobezhkin.loadercalculator.domain.model.LoaderTruckModel
import com.pobezhkin.loadercalculator.domain.model.MiniTruckModel
import com.pobezhkin.loadercalculator.domain.model.UploadTruckModel

import com.pobezhkin.loadercalculator.domain.repository.LoaderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoaderRepositoryImpl @Inject constructor(
    private val loadedTruckDao : LoadedTruckDao,
    private val uploadTruckDao : UploadTruckDao,
    private val miniTruckDao: MiniTruckDao
) : LoaderRepository {

    override fun getAllTrucks() : Flow<List<LoaderTruckModel>> {
       return loadedTruckDao.getAllTrucks()
           .map {entities ->
           entities.map {
               it.toLoaderTruckModel()
           }
       }
    }

    

    override suspend fun addTrucks(eo: Int, fz: Int) {
        loadedTruckDao.insert(LoaderTruckEntity(h_unit = eo, fz_h_unit = fz))
    }


    override suspend fun deleteTrucks(truck : LoaderTruckModel) {
        loadedTruckDao.delete(truck.toLoaderTruckEntity())
    }

    override suspend fun updateTrucks(truck: LoaderTruckModel) {
        loadedTruckDao.update(truck.toLoaderTruckEntity())
    }


    override fun getAllUploads(): Flow<List<UploadTruckModel>> {
       return uploadTruckDao.getAllUploads()
           .map { uploadEntities ->
               uploadEntities.map {
                   it.toUploaderTruckModel()
               }
           }
    }

    override suspend fun addUploadsTruck(uploadEo: Int) {
        uploadTruckDao.insert(UploadTruckEntity(upload = uploadEo))
    }

    override suspend fun deleteUploadsTruck(uploadTruck: UploadTruckModel) {
        uploadTruckDao.delete(uploadTruck.toUploadTruckEntity())
    }

    override suspend fun updatesUploadsTruck(uploadTruck: UploadTruckModel) {
        uploadTruckDao.update(uploadTruck.toUploadTruckEntity())
    }


    override fun getAllMiniTrucks(): Flow<List<MiniTruckModel>> {
        return miniTruckDao.getAllMiniTrucks()
            .map {entities ->
                entities.map {
                    it.toMiniTruckModel()
                }

            }
    }

    override suspend fun addMiniTrucks(mini_eo: Int, mini_fz_eo: Int) {
        miniTruckDao.insert(MiniTruckEntity(mini_eo = mini_eo, mini_fz_eo = mini_fz_eo))
    }

    override suspend fun deleteMiniTrucks(miniTruck: MiniTruckModel) {
        miniTruckDao.delete(miniTruck.toMitiTruckEntity())
    }

    override suspend fun updateMiniTrucks(miniTruck: MiniTruckModel) {
        miniTruckDao.update(miniTruck.toMitiTruckEntity())
    }

}