package com.pobezhkin.loadercalculator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pobezhkin.loadercalculator.data.model.LoaderTruckEntity
import com.pobezhkin.loadercalculator.data.model.UploadTruckEntity

import com.pobezhkin.loadercalculator.domain.model.LoaderTruckModel
import com.pobezhkin.loadercalculator.domain.model.MiniTruckModel
import com.pobezhkin.loadercalculator.domain.model.UploadTruckModel
import com.pobezhkin.loadercalculator.domain.usecase.AddMiniTrucksUseCase
import com.pobezhkin.loadercalculator.domain.usecase.AddTrucksUseCase
import com.pobezhkin.loadercalculator.domain.usecase.DeleteTrucksUseCase
import com.pobezhkin.loadercalculator.domain.usecase.GetTrucksUseCase
import com.pobezhkin.loadercalculator.domain.usecase.GetUploadUseCase
import com.pobezhkin.loadercalculator.domain.usecase.UpdateTruckUseCase
import com.pobezhkin.loadercalculator.domain.usecase.AddUploadUseCase
import com.pobezhkin.loadercalculator.domain.usecase.DeleteMiniTrucksUseCase
import com.pobezhkin.loadercalculator.domain.usecase.DeleteUploadUseCase
import com.pobezhkin.loadercalculator.domain.usecase.GetMiniTrucksUseCase
import com.pobezhkin.loadercalculator.domain.usecase.UpdateMiniTruckUseCase
import com.pobezhkin.loadercalculator.domain.usecase.UpdateUploadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class WorkingShiftScreenViewModel @Inject constructor(
    private val getTrucksUseCase : GetTrucksUseCase,
    private val addTrucksUseCase : AddTrucksUseCase,
    private val updateTruckUseCase: UpdateTruckUseCase,
    private val deleteTrucksUseCase : DeleteTrucksUseCase,

    private val getUploadUseCase: GetUploadUseCase,
    private val addUploadUseCase: AddUploadUseCase,
    private val updateUploadUseCase: UpdateUploadUseCase,
    private val deleteUploadUseCase: DeleteUploadUseCase,

    private val getMiniTrucksUseCase: GetMiniTrucksUseCase,
    private val addMiniTrucksUseCase: AddMiniTrucksUseCase,
    private val updateMiniTruckUseCase: UpdateMiniTruckUseCase,
    private val deleteMiniTrucksUseCase: DeleteMiniTrucksUseCase

) : ViewModel() {

      private val _trucks = MutableStateFlow<List<LoaderTruckModel>>(emptyList())
        val trucks : StateFlow<List<LoaderTruckModel>> = _trucks.asStateFlow()

    private val _uploads = MutableStateFlow<List<UploadTruckModel>>(emptyList())
        val uploads : StateFlow<List<UploadTruckModel>> = _uploads.asStateFlow()

    private val _miniTrucks = MutableStateFlow<List<MiniTruckModel>>(emptyList())

    val miniTruck: StateFlow<List<MiniTruckModel>> = _miniTrucks.asStateFlow()

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

            viewModelScope.launch {
                getMiniTrucksUseCase().collect {  miniTruckList ->
                    _miniTrucks.value = miniTruckList

                }
            }



        }

    fun addMiniTrucks(miniEo: Int, mini_fz_eo : Int ){
        viewModelScope.launch {
            addMiniTrucksUseCase (miniEo , mini_fz_eo)
        }

    }

    fun deleteMiniTrucks(miniTruckModel : MiniTruckModel  ){
        viewModelScope.launch {
            deleteMiniTrucksUseCase(miniTruckModel)
        }
    }

    fun updateMiniTrucks(miniTruckModel: MiniTruckModel){
        viewModelScope.launch {
            updateMiniTruckUseCase(miniTruckModel)
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

    fun deleteUploadsTruck(uploadTruckModel: UploadTruckModel){
        viewModelScope.launch {
            deleteUploadUseCase(uploadTruckModel)
        }
    }


    fun updateUploadTruck(uploadTruckModel: UploadTruckModel){
        viewModelScope.launch {
            updateUploadUseCase(uploadTruckModel)
        }
    }



}