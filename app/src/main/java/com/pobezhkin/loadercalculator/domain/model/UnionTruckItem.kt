package com.pobezhkin.loadercalculator.domain.model

sealed class UnionTruckItem {

    data class LoadingTruck(val truck: LoaderTruckModel) : UnionTruckItem()
    data class UpLoadingTruck(val upload: UploadTruckModel) : UnionTruckItem()

}