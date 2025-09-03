package com.pobezhkin.loadercalculator.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mini_loaded_trucks")
data class MiniTruckEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "miniTruck_eo")
    val mini_eo: Int,
    @ColumnInfo(name = "miniTruck_fz_eo")
    val mini_fz_eo: Int
)