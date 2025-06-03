package com.pobezhkin.loadercalculator.domain.usecase

import com.pobezhkin.loadercalculator.domain.model.LoaderTruckModel
import com.pobezhkin.loadercalculator.domain.repository.LoaderRepository
import javax.inject.Inject

class UpdateTruckUseCase @Inject constructor(
    private val loaderRepository: LoaderRepository
) {
    suspend operator fun invoke(loaderTruckModel: LoaderTruckModel){
        loaderRepository.updateTrucks(loaderTruckModel)
    }
}