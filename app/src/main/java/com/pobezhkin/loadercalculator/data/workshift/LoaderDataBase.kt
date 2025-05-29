package com.pobezhkin.loadercalculator.data.workshift

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pobezhkin.loadercalculator.data.model.LoaderTruckEntity


@Database(entities = [LoaderTruckEntity::class], version = 1)
abstract class LoaderDataBase: RoomDatabase() {

abstract fun truckDao(): LoadedTruckDao

}