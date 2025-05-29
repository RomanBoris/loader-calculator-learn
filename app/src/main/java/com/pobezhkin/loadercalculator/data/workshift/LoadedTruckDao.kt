package com.pobezhkin.loadercalculator.data.workshift

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.pobezhkin.loadercalculator.data.model.LoaderTruckEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoadedTruckDao {

    @Query("SELECT * FROM loaded_trucks")
    fun getAllTrucks(): Flow<List<LoaderTruckEntity>>

    @Insert
    suspend fun insert(truck : LoaderTruckEntity)

    @Delete
    suspend fun delete(truck : LoaderTruckEntity)



}