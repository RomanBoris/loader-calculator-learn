package com.pobezhkin.loadercalculator.domain.usecase

import com.pobezhkin.loadercalculator.domain.model.UploadTruckModel
import com.pobezhkin.loadercalculator.domain.repository.LoaderRepository
import javax.inject.Inject

class DeleteUploadUseCase @Inject constructor(
    private val loaderRepository: LoaderRepository
) {
    suspend operator fun invoke (uploadTruckModel: UploadTruckModel){
        loaderRepository.deleteUploadsTruck(uploadTruckModel)
    }
}