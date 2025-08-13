package com.pobezhkin.loadercalculator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pobezhkin.loadercalculator.data.model.LoaderTruckEntity
import com.pobezhkin.loadercalculator.data.model.UploadTruckEntity

import com.pobezhkin.loadercalculator.domain.model.LoaderTruckModel
import com.pobezhkin.loadercalculator.domain.model.UploadTruckModel
import com.pobezhkin.loadercalculator.domain.usecase.AddTrucksUseCase
import com.pobezhkin.loadercalculator.domain.usecase.DeleteTrucksUseCase
import com.pobezhkin.loadercalculator.domain.usecase.GetTrucksUseCase
import com.pobezhkin.loadercalculator.domain.usecase.GetUploadUseCase
import com.pobezhkin.loadercalculator.domain.usecase.UpdateTruckUseCase
import com.pobezhkin.loadercalculator.domain.usecase.AddUploadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkingShiftScreenViewModel @Inject constructor(
    private val getTrucksUseCase : GetTrucksUseCase,
    private val deleteTrucksUseCase : DeleteTrucksUseCase,
    private val addTrucksUseCase : AddTrucksUseCase,
    private val updateTruckUseCase: UpdateTruckUseCase,
    private val addUploadUseCase: AddUploadUseCase,
    private val getUploadUseCase: GetUploadUseCase
) : ViewModel() {

      private val _trucks = MutableStateFlow<List<LoaderTruckModel>>(emptyList())
        val trucks : StateFlow<List<LoaderTruckModel>> = _trucks.asStateFlow()

    private val _uploads = MutableStateFlow<List<UploadTruckModel>>(emptyList())
        val uploads : StateFlow<List<UploadTruckModel>> = _uploads.asStateFlow()

        init {
            // Подписываемся на поток данных из репозитория
            viewModelScope.launch {
                getTrucksUseCase().collect { trucksList ->
                    _trucks.value = trucksList
                }
            }

            viewModelScope.launch {
                getUploadUseCase().collect { uploadsList ->
                    _uploads.value = uploadsList
                }
            }
        }





        fun addTrucks(eo: Int, fz : Int ){
            viewModelScope.launch {
                addTrucksUseCase(eo , fz)
            }

        }

        fun deleteTrucks(loadedTruckModel : LoaderTruckModel  ){
            viewModelScope.launch {
               deleteTrucksUseCase(loadedTruckModel)
            }
        }

    fun updateTrucks(loadedTruckModel: LoaderTruckModel){
        viewModelScope.launch {
            updateTruckUseCase(loadedTruckModel)
        }
    }

    fun addUpload(uploadEo: Int){
        viewModelScope.launch {
            addUploadUseCase(uploadEo)
        }
    }



}