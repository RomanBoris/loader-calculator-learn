package com.pobezhkin.loadercalculator.data.workshift

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pobezhkin.loadercalculator.data.model.LoaderTruckEntity
import com.pobezhkin.loadercalculator.data.model.UploadTruckEntity
import com.pobezhkin.loadercalculator.data.workshift.repository.UploadTruckDao


@Database(entities = [LoaderTruckEntity::class, UploadTruckEntity::class], version = 2)
abstract class LoaderDataBase: RoomDatabase() {

abstract fun truckDao(): LoadedTruckDao
abstract fun uploadTruckDao(): UploadTruckDao

}