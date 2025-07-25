package com.pobezhkin.loadercalculator.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("uploader_trucks")
data class UploadTruckEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("upload_eo")
    val upload: Int
)
