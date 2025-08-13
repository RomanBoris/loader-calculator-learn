package com.pobezhkin.loadercalculator.domain.usecase

import com.pobezhkin.loadercalculator.domain.model.UploadTruckModel
import com.pobezhkin.loadercalculator.domain.repository.LoaderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUploadUseCase @Inject constructor(
    private val loaderRepository: LoaderRepository

) {

    operator fun invoke() : Flow<List<UploadTruckModel>> = loaderRepository.getAllUploads()

}