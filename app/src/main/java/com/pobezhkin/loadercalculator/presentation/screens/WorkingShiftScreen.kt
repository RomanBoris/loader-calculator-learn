package com.pobezhkin.loadercalculator.presentation.screens

import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.DefaultTab.AlbumsTab.value
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pobezhkin.loadercalculator.R
import com.pobezhkin.loadercalculator.domain.model.LoaderTruckModel
import com.pobezhkin.loadercalculator.domain.model.WorkType
import com.pobezhkin.loadercalculator.presentation.viewmodel.WorkingShiftScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenAddCar(
    viewModel: WorkingShiftScreenViewModel = hiltViewModel()
) {

    var openDialogUploading by remember { mutableStateOf(false) }
    var openEditDialog by remember { mutableStateOf(false) }// Для редактирования
    var selectedTruck by remember { mutableStateOf<LoaderTruckModel?>(null) }// Выбранный грузо
    var workType by remember { mutableStateOf(WorkType.UPLOADING) }
    val lazyColumnState = rememberLazyListState()
    val trucks by viewModel.trucks.collectAsState(initial = emptyList())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(
                        stringResource(R.string.list_of_cars),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.add_car),
                fontSize = 30.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            LazyColumn(
                state = lazyColumnState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {

                items(trucks) { loaderTruck ->

                    TruckItem(
                        loadedTruckModel = loaderTruck,
                        deleteElement = { viewModel.deleteTrucks(loaderTruck) },
                        onLongClick = {
                            selectedTruck = loaderTruck
                            openEditDialog = true
                        }
                    )

                }
            }

            // ВЫЗВАТЬ АЛЕРДИАЛОГ
            WorkAlertDialog(

                openDialog = openEditDialog,
                onDismiss = { openEditDialog = false },
                onConfirm = { amount, freeze ->
                    Log.d("onConfirm"," EO - $amount, FZ - $freeze")
                    // Передаем данные во ViewModel
                    viewModel.addTrucks(eo = amount, fz =  freeze)
                    openEditDialog = false
                }
            )

            selectedTruck?.let { truck ->
                WorkAlertDialog(

                    openDialog = openEditDialog,
                    initialEo = truck.h_unit,
                    initialFz = truck.fz_h_unit,
                    onDismiss = {
                        openEditDialog = false
                        selectedTruck = null
                    },
                    onConfirm = { amount , freeze ->

                        val eoValue = amount
                        val freezeValue = freeze

                        viewModel.updateTrucks(truck.copy(h_unit = eoValue, fz_h_unit = freezeValue))
                        openEditDialog = false
                        selectedTruck = null
                    }
                )


            }

            UploadingTrack(
                openDialog = openDialogUploading,
                onDismiss = {openDialogUploading = false},
                onConfirm = { uploadEo ->

                    viewModel.addTrucks(eo = uploadEo, fz = 0 )

                }
            )



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        workType = WorkType.UPLOADING
                        openDialogUploading = true
                    },
                ) {
                    Text(text = stringResource(R.string.uploading))
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {  workType = WorkType.LOADING
                        openDialogUploading = true
                              },
                ) {
                    Text(text = stringResource(R.string._20_weight))
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        workType = WorkType.LOADING
                        openDialogUploading = true

                    },
                ) {
                    Text(text = stringResource(R.string._12_5_weight))
                }
            }
        }
    }
}

/*onClick = { viewModel.addTrucks(trucks.size + 1, trucks.size + 1  )},*/

