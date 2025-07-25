package com.pobezhkin.loadercalculator.data.workshift.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pobezhkin.loadercalculator.data.model.UploadTruckEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UploadTruckDao {

    @Query("SELECT * FROM uploader_trucks")
    fun getAllUploads() : Flow<List<UploadTruckEntity>>

    @Insert
    suspend fun insert(upload: UploadTruckEntity)

    @Update
    suspend fun update(upload : UploadTruckEntity)

    @Delete
    suspend fun delete(upload: UploadTruckEntity)

}