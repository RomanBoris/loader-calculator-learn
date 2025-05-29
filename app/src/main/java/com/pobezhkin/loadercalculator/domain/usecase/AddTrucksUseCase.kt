package com.pobezhkin.loadercalculator.domain.usecase

import com.pobezhkin.loadercalculator.domain.repository.LoaderRepository
import javax.inject.Inject


class AddTrucksUseCase @Inject constructor(
    private val loaderRepository  : LoaderRepository
) {
    suspend operator fun invoke(eo: Int, fz: Int){
        loaderRepository.addTrucks(eo, fz)
    }
}