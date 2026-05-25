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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pobezhkin.loadercalculator.R
import com.pobezhkin.loadercalculator.domain.model.LoaderTruckModel
import com.pobezhkin.loadercalculator.domain.model.MiniTruckModel
import com.pobezhkin.loadercalculator.domain.model.UnionTruckItem
import com.pobezhkin.loadercalculator.domain.model.UploadTruckModel
import com.pobezhkin.loadercalculator.domain.model.WorkType
import com.pobezhkin.loadercalculator.presentation.screens.mainScreen.components.HoursEditDialog
import com.pobezhkin.loadercalculator.presentation.screens.mainScreen.components.TruckItem
import com.pobezhkin.loadercalculator.presentation.screens.mainScreen.components.TruckPatternBackground
import com.pobezhkin.loadercalculator.presentation.screens.mainScreen.components.UploadTruckItem
import com.pobezhkin.loadercalculator.presentation.screens.mainScreen.components.UploadingTrack
import com.pobezhkin.loadercalculator.presentation.screens.mainScreen.components.WorkAlertDialog
import com.pobezhkin.loadercalculator.ui.theme.BluePalette

@Composable
fun ScreenAddCar(
    viewModel: WorkingShiftScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    ScreenAddCarContent(
        uiState = uiState,
        onSaveShift = { viewModel.saveShiftToHistory() },
        onAddTruck = { amount, freeze -> viewModel.addTrucks(amount, freeze) },
        onUpdateTruck = { viewModel.updateTrucks(it) },
        onDeleteTruck = { viewModel.deleteTrucks(it) },
        onAddMiniTruck = { amount, freeze -> viewModel.addMiniTrucks(amount, freeze) },
        onUpdateMiniTruck = { viewModel.updateMiniTrucks(it) },
        onDeleteMiniTruck = { viewModel.deleteMiniTrucks(it) },
        onAddUpload = { viewModel.addUpload(it) },
        onUpdateUpload = { viewModel.updateUploadTruck(it) },
        onDeleteUpload = { viewModel.deleteUploadsTruck(it) },
        onSaveHours = { viewModel.saveHoursWorked(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ScreenAddCarContent(
    uiState: WorkingShiftUiState,
    onSaveShift: () -> Unit,
    onAddTruck: (Int, Int) -> Unit,
    onUpdateTruck: (LoaderTruckModel) -> Unit,
    onDeleteTruck: (LoaderTruckModel) -> Unit,
    onAddMiniTruck: (Int, Int) -> Unit,
    onUpdateMiniTruck: (MiniTruckModel) -> Unit,
    onDeleteMiniTruck: (MiniTruckModel) -> Unit,
    onAddUpload: (Int) -> Unit,
    onUpdateUpload: (UploadTruckModel) -> Unit,
    onDeleteUpload: (UploadTruckModel) -> Unit,
    onSaveHours: (Double) -> Unit
) {
    var openAddUploadingDialog by remember { mutableStateOf(false) }
    var openEditUploadingDialog by remember { mutableStateOf(false) }
    var openDialogLoading by remember { mutableStateOf(false) }
    var openEditDialog by remember { mutableStateOf(false) }
    var openHoursDialog by remember { mutableStateOf(false) }
    var openSaveConfirmDialog by remember { mutableStateOf(false) }
    var selectedTruck by remember { mutableStateOf<LoaderTruckModel?>(null) }
    var selectedUploadTrack by remember { mutableStateOf<UploadTruckModel?>(null) }
    var workType by remember { mutableStateOf(WorkType.LOADING_20_T) }
    var selectedMiniTruck by remember { mutableStateOf<MiniTruckModel?>(null) }

    val lazyColumnState = rememberLazyListState()

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
                val hoursText = if (uiState.hoursWorked % 1.0 == 0.0)
                    uiState.hoursWorked.toInt().toString() else uiState.hoursWorked.toString()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "СМЕНА $hoursText ЧАСОВ",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        color = BluePalette.TextSecondary
                    )
                    IconButton(onClick = { openHoursDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Редактировать часы смены",
                            tint = BluePalette.TextSecondary
                        )
                    }
                }

                Text(
                    text = "Сделано: ${"%.2f".format(uiState.performancePercent)}%",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 30.sp,
                    color = BluePalette.TextPrimary,
                    modifier = Modifier.padding(bottom = 16.dp)
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
                                    deleteElement = { onDeleteTruck(item.truck) },
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
                                    deleteElement = { onDeleteUpload(item.upload) },
                                    onLongClick = {
                                        selectedUploadTrack = item.upload
                                        openEditUploadingDialog = true
                                    }
                                )
                            }
                            is UnionTruckItem.LoadingMiniTruck -> {
                                TruckItem(
                                    variableTruck = item.miniTruck,
                                    deleteElement = { onDeleteMiniTruck(item.miniTruck) },
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

        FloatingActionButton(
            onClick = { openSaveConfirmDialog = true },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 18.dp, top = 30.dp)
                .size(48.dp),
            containerColor = BluePalette.SaveGreen,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = "Сохранить смену в историю"
            )
        }

        FloatingActionButton(
            onClick = { },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 18.dp, top = 30.dp)
                .size(48.dp),
            containerColor = BluePalette.SettingsGray,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Настройки"
            )
        }

        // Диалоги
        if (openSaveConfirmDialog) {
            AlertDialog(
                onDismissRequest = { openSaveConfirmDialog = false },
                title = { Text("Сохранить смену?") },
                text = { Text("Вы уверены что хотите сохранить смену в историю?") },
                confirmButton = {
                    TextButton(onClick = {
                        onSaveShift()
                        openSaveConfirmDialog = false
                    }) { Text("Сохранить") }
                },
                dismissButton = {
                    TextButton(onClick = { openSaveConfirmDialog = false }) { Text("Отмена") }
                }
            )
        }

        if (openDialogLoading) {
            WorkAlertDialog(
                openDialog = openDialogLoading,
                initialEo = 0,
                initialFz = 0,
                onDismiss = { openDialogLoading = false },
                onConfirm = { amount, freeze ->
                    when (workType) {
                        WorkType.LOADING_20_T -> onAddTruck(amount, freeze)
                        WorkType.LOADING_12_7_5_T -> onAddMiniTruck(amount, freeze)
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
                                onUpdateTruck(truck.copy(h_unit = amount, fz_h_unit = freeze))
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
                                onUpdateMiniTruck(miniTruck.copy(mini_eo = amount, mini_fz_eo = freeze))
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
                        onUpdateUpload(upLoadTrack.copy(upload = newUploadEo))
                        openEditUploadingDialog = false
                    }
                )
            }
        }

        if (openHoursDialog) {
            HoursEditDialog(
                currentHours = uiState.hoursWorked,
                onDismiss = { openHoursDialog = false },
                onConfirm = { hours ->
                    onSaveHours(hours)
                    openHoursDialog = false
                }
            )
        }

        if (openAddUploadingDialog) {
            UploadingTrack(
                openDialog = openAddUploadingDialog,
                initialEo = null,
                onDismiss = { openAddUploadingDialog = false },
                onConfirm = { newUploadEo ->
                    onAddUpload(newUploadEo)
                    openAddUploadingDialog = false
                }
            )
        }
    } // Box
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ScreenAddCarPreview() {
    ScreenAddCarContent(
        uiState = WorkingShiftUiState(
            hoursWorked = 11.0,
            performancePercent = 86.18,
            trucks = listOf(
                LoaderTruckModel(id = 1, h_unit = 36, fz_h_unit = 8),
                LoaderTruckModel(id = 2, h_unit = 36, fz_h_unit = 0),
            ),
            uploads = listOf(
                UploadTruckModel(id = 1, upload = 33),
                UploadTruckModel(id = 2, upload = 33),
                UploadTruckModel(id = 3, upload = 33),
            ),
            miniTrucks = listOf(
                MiniTruckModel(id = 1, mini_eo = 10, mini_fz_eo = 0)
            )
        ),
        onSaveShift = {},
        onAddTruck = { _, _ -> },
        onUpdateTruck = {},
        onDeleteTruck = {},
        onAddMiniTruck = { _, _ -> },
        onUpdateMiniTruck = {},
        onDeleteMiniTruck = {},
        onAddUpload = {},
        onUpdateUpload = {},
        onDeleteUpload = {},
        onSaveHours = {}
    )
}
