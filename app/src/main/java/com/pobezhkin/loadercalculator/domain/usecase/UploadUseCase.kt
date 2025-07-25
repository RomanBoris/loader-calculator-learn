package com.pobezhkin.loadercalculator.domain.usecase

import com.pobezhkin.loadercalculator.domain.repository.LoaderRepository
import javax.inject.Inject

class UploadUseCase @Inject constructor (
        private val loaderRepository: LoaderRepository
) {
        suspend operator fun invoke(uploadEo: Int){
            loaderRepository.addUploadsTruck(uploadEo)
        }

}