package com.pobezhkin.loadercalculator.data.workshift

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loaded_trucks")
data  class LoadedTruck(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "total_eo")
    val processingUnit : Int,
    @ColumnInfo(name = "freeze_eo")
    val processingUnitFreeze : Int
  )