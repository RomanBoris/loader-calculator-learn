package com.pobezhkin.loadercalculator.domain.usecase

import com.pobezhkin.loadercalculator.domain.repository.LoaderRepository
import javax.inject.Inject

class AddMiniTrucksUseCase @Inject constructor(
    private val loaderRepository  : LoaderRepository
) {
    suspend operator fun invoke(mini_eo: Int, mini_fz_eo: Int){
        loaderRepository.addMiniTrucks(mini_eo, mini_fz_eo)
    }
}