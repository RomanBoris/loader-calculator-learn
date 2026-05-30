package com.pobezhkin.loadercalculator.presentation.screens.mainScreen

import com.pobezhkin.loadercalculator.domain.model.LoaderTruckModel
import com.pobezhkin.loadercalculator.domain.model.MiniTruckModel
import com.pobezhkin.loadercalculator.domain.model.UploadTruckModel

sealed class UnionTruckItem {

    data class LoadingTruck(val truck: LoaderTruckModel) : UnionTruckItem()

    data class LoadingMiniTruck(val miniTruck: MiniTruckModel) : UnionTruckItem()
    data class UpLoadingTruck(val upload: UploadTruckModel) : UnionTruckItem()

}