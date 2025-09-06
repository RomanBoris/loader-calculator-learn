package com.pobezhkin.loadercalculator.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pobezhkin.loadercalculator.R
import com.pobezhkin.loadercalculator.domain.model.LoaderTruckModel
import com.pobezhkin.loadercalculator.domain.model.UnionTruckItem
import com.pobezhkin.loadercalculator.domain.model.UploadTruckModel
import com.pobezhkin.loadercalculator.domain.model.WorkType
import com.pobezhkin.loadercalculator.presentation.viewmodel.WorkingShiftScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenAddCar(
    viewModel: WorkingShiftScreenViewModel = hiltViewModel()
) {

    var openAddUploadingDialog by remember { mutableStateOf(false) }
    var openEditUploadingDialog by remember { mutableStateOf(false) }
    var openDialogLoading by remember { mutableStateOf(false) }
    var openEditDialog by remember { mutableStateOf(false) }// Для редактирования
    var selectedTruck by remember { mutableStateOf<LoaderTruckModel?>(null) }// Выбранный грузо
    var selectedUploadTrack by remember { mutableStateOf<UploadTruckModel?>(null) }// Выбранный грузо
    var workType by remember { mutableStateOf(WorkType.LOADING) }
    val lazyColumnState = rememberLazyListState()
    val trucks by viewModel.trucks.collectAsState(initial = emptyList())
    val upLoad by viewModel.uploads.collectAsState(initial = emptyList())

    val unionTruckList = remember(trucks, upLoad) {
        trucks.map{ UnionTruckItem.LoadingTruck(it) } + upLoad.map { UnionTruckItem.UpLoadingTruck(it) }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().systemBarsPadding(),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                title = {
                    Text(
                        stringResource(R.string.list_of_cars),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        },

                containerColor = androidx.compose.ui.graphics.Color.Transparent
    ) { innerPadding ->
        TruckPatternBackground()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.add_car),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 30.sp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            LazyColumn(

                state = lazyColumnState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(innerPadding)

            ) {

                items(unionTruckList) { LoadAndUpload ->
                    when (LoadAndUpload) {
                    is UnionTruckItem.LoadingTruck -> {
                        TruckItem(
                            loadedTruckModel = LoadAndUpload.truck,
                            deleteElement = { viewModel.deleteTrucks(LoadAndUpload.truck) },
                            onLongClick = {
                                selectedTruck = LoadAndUpload.truck
                                openEditDialog = true
                            }
                        )
                    }

                        is UnionTruckItem.UpLoadingTruck -> {
                            UploadTruckItem(
                                upLoaderTruckModel = LoadAndUpload.upload,
                                deleteElement = { viewModel.deleteUploadsTruck(LoadAndUpload.upload) },

                            onLongClick = {
                                selectedUploadTrack = LoadAndUpload.upload
                                openEditUploadingDialog = true
                            }
                            )

                        }


                }
            }


            }

            // ВЫЗВАТЬ АЛЕРДИАЛОГ
            WorkAlertDialog(

                openDialog = openDialogLoading,
                initialEo = null, // Для добавления - null
                initialFz = null, // Для добавления - null
                onDismiss = { openDialogLoading = false },
                onConfirm = { amount, freeze ->
                    viewModel.addTrucks(eo = amount, fz = freeze)
                    openDialogLoading = false
                }
            )

            selectedTruck?.let { truck ->
                WorkAlertDialog(


                    openDialog = openEditDialog,
                    initialEo = truck.h_unit, // Для редактирования - текущие значения
                    initialFz = truck.fz_h_unit,
                    onDismiss = {
                        openEditDialog = false
                        selectedTruck = null
                    },
                    onConfirm = { amount, freeze ->
                        viewModel.updateTrucks(truck.copy(h_unit = amount, fz_h_unit = freeze))
                        openEditDialog = false
                        selectedTruck = null
                    }
                )

            }

            selectedUploadTrack?.let{ upLoadTrack ->

                UploadingTrack(
                    openDialog = openEditUploadingDialog,
                    initialEo = upLoadTrack.upload, // Для редактирования - текущее значение
                    onDismiss = { openEditUploadingDialog = false },
                    onConfirm = { newUploadEo ->
                        viewModel.updateUploadTruck(upLoadTrack.copy(upload = newUploadEo))
                        openEditUploadingDialog = false
                    }
                )
            }

            UploadingTrack(
                openDialog = openAddUploadingDialog,
                initialEo = null, // Для добавления - null
                onDismiss = { openAddUploadingDialog = false },
                onConfirm = { newUploadEo ->
                    viewModel.addUpload(newUploadEo)
                    openAddUploadingDialog = false
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
                        openAddUploadingDialog = true
                    },
                ) {
                    Text(text = stringResource(R.string.uploading))
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {  workType = WorkType.LOADING
                        openDialogLoading = true
                              },
                ) {
                    Text(text = stringResource(R.string._20_weight))
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        workType = WorkType.LOADING
                        openDialogLoading = true

                    },
                ) {
                    Text(text = stringResource(R.string._12_5_weight))
                }
            }
        }
    }
}

/*onClick = { viewModel.addTrucks(trucks.size + 1, trucks.size + 1  )},*/

