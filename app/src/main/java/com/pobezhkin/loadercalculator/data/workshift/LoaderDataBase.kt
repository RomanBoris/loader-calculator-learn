package com.pobezhkin.loadercalculator.data.workshift

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pobezhkin.loadercalculator.data.model.LoaderTruckEntity
import com.pobezhkin.loadercalculator.data.model.MiniTruckEntity
import com.pobezhkin.loadercalculator.data.model.UploadTruckEntity
import com.pobezhkin.loadercalculator.data.workshift.repository.MiniTruckDao
import com.pobezhkin.loadercalculator.data.workshift.repository.UploadTruckDao


@Database(entities = [LoaderTruckEntity::class, UploadTruckEntity::class, MiniTruckEntity::class], version = 3)
abstract class LoaderDataBase: RoomDatabase() {

abstract fun truckDao(): LoadedTruckDao

abstract fun miniTruckDao(): MiniTruckDao

abstract fun uploadTruckDao(): UploadTruckDao

}

//1 весия -  большие траки
//2 версия - добавление выгрузки
//3 версия - сейчас с добавлением минитраков