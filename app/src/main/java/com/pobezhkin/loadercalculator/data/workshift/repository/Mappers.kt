package com.pobezhkin.loadercalculator.data.workshift.repository

import com.pobezhkin.loadercalculator.data.model.LoaderTruckEntity
import com.pobezhkin.loadercalculator.data.model.MiniTruckEntity
import com.pobezhkin.loadercalculator.data.model.UploadTruckEntity
import com.pobezhkin.loadercalculator.domain.model.LoaderTruckModel
import com.pobezhkin.loadercalculator.domain.model.MiniTruckModel
import com.pobezhkin.loadercalculator.domain.model.UploadTruckModel


fun LoaderTruckEntity.toLoaderTruckModel() = LoaderTruckModel(
    id = id,
    h_unit = h_unit,
    fz_h_unit = fz_h_unit,
)

fun LoaderTruckModel.toLoaderTruckEntity() = LoaderTruckEntity(
    id = id,
    h_unit = h_unit,
    fz_h_unit = fz_h_unit
)

fun MiniTruckEntity.toMiniTruckModel() = MiniTruckModel(
    id = id,
    mini_eo = mini_eo,
    mini_fz_eo = mini_fz_eo
)

fun MiniTruckModel.toMitiTruckEntity() = MiniTruckEntity(
    id = id,
    mini_eo = mini_eo,
    mini_fz_eo = mini_fz_eo
)

fun UploadTruckEntity.toUploaderTruckModel() = UploadTruckModel(
    id = id,
    upload = upload
)

fun UploadTruckModel.toUploadTruckEntity() = UploadTruckEntity(
    id = id,
    upload = upload
)