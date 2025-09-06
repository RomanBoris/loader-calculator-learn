package com.pobezhkin.loadercalculator.domain.usecase

import com.pobezhkin.loadercalculator.domain.model.MiniTruckModel
import com.pobezhkin.loadercalculator.domain.repository.LoaderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddMiniTrucksUseCase @Inject constructor(
    private val loaderRepository: LoaderRepository
) {
    suspend operator fun invoke(mini_eo: Int, mini_fz_eo: Int) {
        loaderRepository.addMiniTrucks(mini_eo, mini_fz_eo)
    }
}

class DeleteMiniTrucksUseCase @Inject constructor(
    private val loaderRepository: LoaderRepository
) {
    suspend operator fun invoke(miniTruckModel: MiniTruckModel) {
        loaderRepository.deleteMiniTrucks(miniTruckModel)
    }
}

class GetMiniTrucksUseCase @Inject constructor(
    private val loaderRepository: LoaderRepository
) {

    operator fun invoke(): Flow<List<MiniTruckModel>> = loaderRepository.getAllMiniTrucks()
}

class UpdateMiniTruckUseCase @Inject constructor(
    private val loaderRepository: LoaderRepository
) {
    suspend operator fun invoke(miniTruckModel: MiniTruckModel) {
        loaderRepository.updateMiniTrucks(miniTruckModel)
    }
}