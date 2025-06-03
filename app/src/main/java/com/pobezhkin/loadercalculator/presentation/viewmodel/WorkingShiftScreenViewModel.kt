package com.pobezhkin.loadercalculator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pobezhkin.loadercalculator.data.model.LoaderTruckEntity

import com.pobezhkin.loadercalculator.data.workshift.repository.LoaderRepositoryImpl
import com.pobezhkin.loadercalculator.domain.model.LoaderTruckModel
import com.pobezhkin.loadercalculator.domain.usecase.AddTrucksUseCase
import com.pobezhkin.loadercalculator.domain.usecase.DeleteTrucksUseCase
import com.pobezhkin.loadercalculator.domain.usecase.GetTrucksUseCase
import com.pobezhkin.loadercalculator.domain.usecase.UpdateTruckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkingShiftScreenViewModel @Inject constructor(
    private val getTrucksUseCase : GetTrucksUseCase,
    private val deleteTrucksUseCase : DeleteTrucksUseCase  ,
    private val addTrucksUseCase : AddTrucksUseCase,
    private val updateTruckUseCase: UpdateTruckUseCase
) : ViewModel() {

      /*  private val _trucks = MutableStateFlow<List<LoaderTruckEntity>>(emptyList())
        val trucks : StateFlow<List<LoaderTruckEntity>> = _trucks.asStateFlow() */

    val trucks = getTrucksUseCase()

      /* init {
            // Подписываемся на поток данных из репозитория
            viewModelScope.launch {
                loaderRepository.allgetAllTrucks.collect{ trucksList ->
                    _trucks.value = trucksList
                }
            }
        }*/

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

}