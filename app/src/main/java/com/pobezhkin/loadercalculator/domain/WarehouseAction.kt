package com.pobezhkin.loadercalculator.domain

data class WarehouseAction(
    val id: Int = 0,
    val typesOfWorks: TypesOfWorks,
    val palletes: Int,
    val timesWork : Float
)