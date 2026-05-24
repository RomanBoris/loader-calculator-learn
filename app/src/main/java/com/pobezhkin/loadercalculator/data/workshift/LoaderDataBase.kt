package com.pobezhkin.loadercalculator.data.workshift

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pobezhkin.loadercalculator.data.model.LoaderTruckEntity
import com.pobezhkin.loadercalculator.data.model.MiniTruckEntity
import com.pobezhkin.loadercalculator.data.model.ShiftHistoryEntity
import com.pobezhkin.loadercalculator.data.model.UploadTruckEntity

@Database(
    entities = [
        LoaderTruckEntity::class,
        UploadTruckEntity::class,
        MiniTruckEntity::class,
        ShiftHistoryEntity::class
    ],
    version = 4
)
abstract class LoaderDataBase : RoomDatabase() {

    abstract fun truckDao(): LoadedTruckDao

    abstract fun miniTruckDao(): MiniTruckDao

    abstract fun uploadTruckDao(): UploadTruckDao

    abstract fun shiftHistoryDao(): ShiftHistoryDao
}

//1 версия - большие траки
//2 версия - добавление выгрузки
//3 версия - добавление минитраков
//4 версия - добавление истории смен