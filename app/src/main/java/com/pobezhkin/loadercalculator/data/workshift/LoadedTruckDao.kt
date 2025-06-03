package com.pobezhkin.loadercalculator.data.workshift

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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

    @Update
    suspend fun update(truck : LoaderTruckEntity)



}