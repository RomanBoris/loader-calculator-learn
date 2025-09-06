package com.pobezhkin.loadercalculator.presentation.screens


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
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
    var openEditDialog by remember { mutableStateOf(false) } // Для редактирования
    var selectedTruck by remember { mutableStateOf<LoaderTruckModel?>(null) }
    var selectedUploadTrack by remember { mutableStateOf<UploadTruckModel?>(null) }
    var workType by remember { mutableStateOf(WorkType.LOADING) }

    val lazyColumnState = rememberLazyListState()
    val trucks by viewModel.trucks.collectAsState(initial = emptyList())
    val upLoad by viewModel.uploads.collectAsState(initial = emptyList())

    val unionTruckList = remember(trucks, upLoad) {
        trucks.map { UnionTruckItem.LoadingTruck(it) } + upLoad.map {
            UnionTruckItem.UpLoadingTruck(
                it
            )
        }
    }



    Scaffold(
        modifier = Modifier.fillMaxSize(),

        containerColor = BluePalette.Background,
        // ВАЖНО: обнуляем системные инсетсы у Scaffold и AppBar
        contentWindowInsets = WindowInsets(0),
        topBar = {

            CenterAlignedTopAppBar(
                modifier = Modifier.padding(16.dp),
                windowInsets = WindowInsets(0),
                colors = centerAlignedTopAppBarColors(
                    containerColor = BluePalette.Background,
                    titleContentColor = BluePalette.TextPrimary,
                    navigationIconContentColor = BluePalette.TextPrimary,
                    actionIconContentColor = BluePalette.TextPrimary
                ),
                title = {

                            Text(
                                text = stringResource(R.string.list_of_cars),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontSize = 30.sp,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(16.dp)
                            )

                }
            )
        }
    ) { innerPadding ->
        // innerPadding уже без системных отступов (но с отступом под AppBar)
        Box(Modifier.fillMaxSize()) {
            // Если нужен чистый цвет — закомментируй
            TruckPatternBackground()

            // Контент: добавим только нижний отступ от навбара
            androidx.compose.foundation.layout.Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding) // отступ под AppBar
                    .navigationBarsPadding() // чтобы кнопки не упирались в навбар
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.add_car),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 30.sp,
                    color = BluePalette.TextPrimary,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                LazyColumn(
                    state = lazyColumnState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(unionTruckList.size) { index ->
                        when (val item = unionTruckList[index]) {
                            is UnionTruckItem.LoadingTruck -> {
                                TruckItem(
                                    loadedTruckModel = item.truck,
                                    deleteElement = { viewModel.deleteTrucks(item.truck) },
                                    onLongClick = {
                                        selectedTruck = item.truck
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
                        }
                    }
                }

                // Диалог добавления погрузки
                WorkAlertDialog(
                    openDialog = openDialogLoading,
                    initialEo = null,
                    initialFz = null,
                    onDismiss = { openDialogLoading = false },
                    onConfirm = { amount, freeze ->
                        viewModel.addTrucks(eo = amount, fz = freeze)
                        openDialogLoading = false
                    }
                )

                // Диалог редактирования погрузки
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
                            viewModel.updateTrucks(truck.copy(h_unit = amount, fz_h_unit = freeze))
                            openEditDialog = false
                            selectedTruck = null
                        }
                    )
                }

                // Диалог редактирования разгрузки
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

                // Диалог добавления разгрузки
                UploadingTrack(
                    openDialog = openAddUploadingDialog,
                    initialEo = null,
                    onDismiss = { openAddUploadingDialog = false },
                    onConfirm = { newUploadEo ->
                        viewModel.addUpload(newUploadEo)
                        openAddUploadingDialog = false
                    }
                )

                // Кнопки снизу
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
                            workType = WorkType.LOADING
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
                            workType = WorkType.LOADING
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
        }
    }

}




/*onClick = { viewModel.addTrucks(trucks.size + 1, trucks.size + 1  )},*/

