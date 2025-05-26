package com.pobezhkin.loadercalculator.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pobezhkin.loadercalculator.data.workshift.LoadedTruck
import com.pobezhkin.loadercalculator.data.workshift.repository.LoaderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkingShiftScreenViewModel @Inject constructor(
    private val loaderRepository : LoaderRepository
) : ViewModel() {

        private val _trucks = MutableStateFlow<List<LoadedTruck>>(emptyList())
        val trucks : StateFlow<List<LoadedTruck>> = _trucks.asStateFlow()

        init {
            // Подписываемся на поток данных из репозитория
            viewModelScope.launch {
                loaderRepository.allTrucks.collect{ trucksList ->
                    _trucks.value = trucksList
                }
            }
        }

        fun addTrucks(eo: Int, fz : Int ){
            viewModelScope.launch {
                loaderRepository.addTrucks(eo, fz)
            }

        }

        fun deleteTrucks(loadedTruck : LoadedTruck){
            viewModelScope.launch {
                loaderRepository.deleteTrucks(loadedTruck)
            }
        }

}