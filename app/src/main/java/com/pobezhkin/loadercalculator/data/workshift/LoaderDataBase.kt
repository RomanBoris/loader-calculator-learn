package com.pobezhkin.loadercalculator.data.workshift

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [LoadedTruck::class], version = 1)
abstract class LoaderDataBase: RoomDatabase() {
abstract fun truckDao(): LoadedTruckDao

    companion object{
        @Volatile
     private var INSTANCE: LoaderDataBase ? = null

        fun getInstance(context : Context): LoaderDataBase{
            return INSTANCE ?: synchronized(this){
                val instanse = Room.databaseBuilder(
                    context.applicationContext,
                    LoaderDataBase::class.java,
                    "loaded_truck"
                ).build()
                INSTANCE = instanse
                instanse
            }
        }

    }

}