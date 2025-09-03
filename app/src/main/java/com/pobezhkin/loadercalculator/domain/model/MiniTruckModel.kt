package com.pobezhkin.loadercalculator.domain.model

import com.pobezhkin.loadercalculator.data.model.MiniTruckEntity

data class MiniTruckModel (
    val id: Int,
    val mini_eo: Int,
    val mini_fz_eo: Int
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
