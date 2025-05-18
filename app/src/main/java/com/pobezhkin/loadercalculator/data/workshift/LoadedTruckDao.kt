package com.pobezhkin.loadercalculator.data.workshift

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow






@Dao
interface LoadedTruckDao {
    @Query("SELECT * FROM loaded_trucks")

    fun getAllTrucks(): Flow<List<LoadedTruck>>
    @Insert
    suspend fun insert(truck : LoadedTruck)

    @Delete
    suspend fun delete(truck : LoadedTruck)



}