package com.pobezhkin.loadercalculator.domain.model

import com.pobezhkin.loadercalculator.data.model.LoaderTruckEntity

data class LoaderTruckModel(
    val id: Int = 0,
    val h_unit: Int = 0,
    val fz_h_unit: Int = 0,
)

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