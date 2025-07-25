package com.pobezhkin.loadercalculator.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loaded_trucks")
data  class LoaderTruckEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo(name = "total_eo")
    val h_unit : Int,
    @ColumnInfo(name = "freeze_eo")
    val fz_h_unit : Int,

  )