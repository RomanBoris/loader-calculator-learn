package com.pobezhkin.loadercalculator.data.workshift

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pobezhkin.loadercalculator.data.model.MiniTruckEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MiniTruckDao {

    @Query("SELECT * FROM mini_loaded_trucks")
    fun getAllMiniTrucks(): Flow<List<MiniTruckEntity>>

    @Insert
    suspend fun insert(mimiTruck: MiniTruckEntity)

    @Delete
    suspend fun delete(miniTruck: MiniTruckEntity)

    @Update
    suspend fun update(miniTruck: MiniTruckEntity)
}
