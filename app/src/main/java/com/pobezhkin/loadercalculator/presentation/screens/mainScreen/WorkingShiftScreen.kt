package com.pobezhkin.loadercalculator.presentation.screens.mainScreen


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pobezhkin.loadercalculator.R
import com.pobezhkin.loadercalculator.domain.model.LoaderTruckModel
import com.pobezhkin.loadercalculator.domain.model.MiniTruckModel
import com.pobezhkin.loadercalculator.domain.model.UnionTruckItem
import com.pobezhkin.loadercalculator.domain.model.UploadTruckModel
import com.pobezhkin.loadercalculator.domain.model.WorkType
import com.pobezhkin.loadercalculator.presentation.screens.mainScreen.components.TruckItem
import com.pobezhkin.loadercalculator.presentation.screens.mainScreen.components.TruckPatternBackground
import com.pobezhkin.loadercalculator.presentation.screens.mainScreen.components.UploadTruckItem
import com.pobezhkin.loadercalculator.presentation.screens.mainScreen.components.UploadingTrack
import com.pobezhkin.loadercalculator.presentation.screens.mainScreen.components.WorkAlertDialog
import com.pobezhkin.loadercalculator.presentation.viewmodel.WorkingShiftScreenViewModel

// Фиксированная палитра (как у погодного)
object BluePalette {
    val Background = Color(0xFF0F1C3A)
    val TextPrimary = Color(0xFFEAEAF7)
    val TextSecondary = Color(0xFFB6BBD7)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ScreenAddCar(
    viewModel: WorkingShiftScreenViewModel = hiltViewModel()
) {

    var openAddUploadingDialog by remember { mutableStateOf(false) }
    var openEditUploadingDialog by remember { mutableStateOf(false) }
    var openDialogLoading by remember { mutableStateOf(false) }
    var openEditDialog by remember { mutableStateOf(false) }
    var selectedTruck by remember { mutableStateOf<LoaderTruckModel?>(null) }
    var selectedUploadTrack by remember { mutableStateOf<UploadTruckModel?>(null) }
    var workType by remember { mutableStateOf(WorkType.LOADING_20_T) }
    var selectedMiniTruck by remember { mutableStateOf<MiniTruckModel?>(null) }

    val lazyColumnState = rememberLazyListState()
    val uiState by viewModel.uiState.collectAsState()

    val unionTruckList = uiState.trucks.map { UnionTruckItem.LoadingTruck(it) } +
            uiState.uploads.map { UnionTruckItem.UpLoadingTruck(it) } +
            uiState.miniTrucks.map { UnionTruckItem.LoadingMiniTruck(it) }

    Box(Modifier.fillMaxSize()) {
        TruckPatternBackground()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxSize()
        ) {
                Text(
                    text = "Сделано: ${"%.2f".format(uiState.performancePercent)}%",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 30.sp,
                    color = BluePalette.TextPrimary,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                LazyColumn(
                    state = lazyColumnState,
                    modifier = Modifier.weight(1f)
                ) {
                    items(
                        items = unionTruckList,
                        key = { item ->
                            when (item) {
                                is UnionTruckItem.LoadingTruck -> "LT_${item.truck.id}"
                                is UnionTruckItem.UpLoadingTruck -> "UT_${item.upload.id}"
                                is UnionTruckItem.LoadingMiniTruck -> "MT_${item.miniTruck.id}"
                            }
                        }
                    ) { item ->
                        when (item) {
                            is UnionTruckItem.LoadingTruck -> {
                                TruckItem(
                                    variableTruck = item.truck,
                                    deleteElement = { viewModel.deleteTrucks(item.truck) },
                                    onLongClick = {
                                        selectedTruck = item.truck
                                        workType = WorkType.LOADING_20_T
                                        openEditDialog = true
                                    }
                                )
                            }
                            is UnionTruckItem.UpLoadingTruck -> {
                                UploadTruckItem(
                                    upLoaderTruckModel = item.upload,
                                    deleteElement = { viewModel.deleteUploadsTruck(item.upload) },
                                    onLongClick = {
                                        selectedUploadTrack = item.upload
                                        openEditUploadingDialog = true
                                    }
                                )
                            }
                            is UnionTruckItem.LoadingMiniTruck -> {
                                TruckItem(
                                    variableTruck = item.miniTruck,
                                    deleteElement = { viewModel.deleteMiniTrucks(item.miniTruck) },
                                    onLongClick = {
                                        selectedMiniTruck = item.miniTruck
                                        workType = WorkType.LOADING_12_7_5_T
                                        openEditDialog = true
                                    }
                                )
                            }
                        }
                    }
                }

                // Кнопки размещаем здесь - над нижним баром
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
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = BluePalette.TextPrimary
                        )
                    ) {
                        Text(text = stringResource(R.string.uploading))
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            workType = WorkType.LOADING_20_T
                            openDialogLoading = true
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = BluePalette.TextPrimary
                        )
                    ) {
                        Text(text = stringResource(R.string._20_weight))
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            workType = WorkType.LOADING_12_7_5_T
                            openDialogLoading = true
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = BluePalette.TextPrimary
                        )
                    ) {
                        Text(text = stringResource(R.string._12_5_weight))
                    }
                }
            }

        // Диалоги
        if (openDialogLoading) {
            WorkAlertDialog(
                openDialog = openDialogLoading,
                initialEo = 0,
                initialFz = 0,
                onDismiss = { openDialogLoading = false },
                onConfirm = { amount, freeze ->
                    when (workType) {
                        WorkType.LOADING_20_T -> viewModel.addTrucks(amount, freeze)
                        WorkType.LOADING_12_7_5_T -> viewModel.addMiniTrucks(amount, freeze)
                        WorkType.UPLOADING -> {}
                    }
                    openDialogLoading = false
                }
            )
        }

        if (openEditDialog) {
            when (workType) {
                WorkType.LOADING_20_T -> {
                    selectedTruck?.let { truck ->
                        WorkAlertDialog(
                            openDialog = openEditDialog,
                            initialEo = truck.h_unit,
                            initialFz = truck.fz_h_unit,
                            onDismiss = {
                                openEditDialog = false
                                selectedTruck = null
                            },
                            onConfirm = { amount, freeze ->
                                viewModel.updateTrucks(
                                    truck.copy(h_unit = amount, fz_h_unit = freeze)
                                )
                                openEditDialog = false
                                selectedTruck = null
                            }
                        )
                    }
                }
                WorkType.LOADING_12_7_5_T -> {
                    selectedMiniTruck?.let { miniTruck ->
                        WorkAlertDialog(
                            openDialog = openEditDialog,
                            initialEo = miniTruck.mini_eo,
                            initialFz = miniTruck.mini_fz_eo,
                            onDismiss = {
                                openEditDialog = false
                                selectedMiniTruck = null
                            },
                            onConfirm = { amount, freeze ->
                                viewModel.updateMiniTrucks(
                                    miniTruck.copy(mini_eo = amount, mini_fz_eo = freeze)
                                )
                                openEditDialog = false
                                selectedMiniTruck = null
                            }
                        )
                    }
                }
                WorkType.UPLOADING -> {}
            }
        }

        if (openEditUploadingDialog) {
            selectedUploadTrack?.let { upLoadTrack ->
                UploadingTrack(
                    openDialog = openEditUploadingDialog,
                    initialEo = upLoadTrack.upload,
                    onDismiss = { openEditUploadingDialog = false },
                    onConfirm = { newUploadEo ->
                        viewModel.updateUploadTruck(upLoadTrack.copy(upload = newUploadEo))
                        openEditUploadingDialog = false
                    }
                )
            }
        }

        if (openAddUploadingDialog) {
            UploadingTrack(
                openDialog = openAddUploadingDialog,
                initialEo = null,
                onDismiss = { openAddUploadingDialog = false },
                onConfirm = { newUploadEo ->
                    viewModel.addUpload(newUploadEo)
                    openAddUploadingDialog = false
                }
            )
        }
    } // Box
}
