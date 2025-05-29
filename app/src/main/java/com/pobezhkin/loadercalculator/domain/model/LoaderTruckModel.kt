package com.pobezhkin.loadercalculator.domain.model

import com.pobezhkin.loadercalculator.data.model.LoaderTruckEntity

data class LoaderTruckModel(
    val id: Int = 0,
    val h_unit: Int = 2,
    val fz_h_unit: Int = 3
)

fun LoaderTruckEntity.toLoaderTruckModel()  = LoaderTruckModel(id, h_unit, h_unit )
fun LoaderTruckModel.toLoaderTruckEntity() = LoaderTruckEntity(id, h_unit, fz_h_unit)