package com.pobezhkin.loadercalculator.domain.model

import com.pobezhkin.loadercalculator.data.model.LoaderTruckEntity
import com.pobezhkin.loadercalculator.data.model.UploadTruckEntity

data class UploadTruckModel(
    val id: Int = 0,
    val upload: Int = 0
)


fun UploadTruckEntity.toUploaderTruckModel() = UploadTruckModel(
    id = id,
    upload = upload
)

fun UploadTruckModel.toUploadTruckEntity() = UploadTruckEntity(
    id = id,
    upload = upload
)

