package com.pobezhkin.loadercalculator.presentation.screens

import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.pobezhkin.loadercalculator.R
import com.pobezhkin.loadercalculator.presentation.viewmodel.WorkingShiftScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenAddCar(
    viewModel : WorkingShiftScreenViewModel = hiltViewModel()
) {
    val eoState = remember { mutableStateOf("") }
    val errorText = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val openDialog = remember{ mutableStateOf(false) }
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

                items(trucks) { loaderTruck  ->

                    TruckItem(
                        loadedTruckModel = loaderTruck,
                        deleteElement = { viewModel.deleteTrucks(loaderTruck) }
                    )

                }
            }

            if (openDialog.value) {
                BasicAlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    }
                ) {
                    Surface(
                        modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                        shape = MaterialTheme.shapes.large,
                        tonalElevation = AlertDialogDefaults.TonalElevation
                    ) {
                        Column(

                            modifier = Modifier.padding(16.dp)) {
                            Text(
                                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                                textAlign = TextAlign.Center,
                                text ="Введите количество \n" +
                                        " принятых из под стола ЕО:"
                                ,
                            )

                            //проверяем
                            LaunchedEffect(Unit) {
                                focusRequester.requestFocus()
                            }

                            TextField(
                                modifier = Modifier.focusRequester(focusRequester),
                                value = eoState.value,
                              onValueChange = {newText ->
                                  when {
                                      newText.isEmpty() ->{
                                          eoState.value = ""
                                          errorText.value = true
                                      }
                                      newText.all { it.isDigit() } ->{
                                          eoState.value = newText
                                          errorText.value = false
                                      }

                                  }
                              },

                                keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                ),

                                label = { Text("ЕО:") },

                                singleLine = true,

                                isError = errorText.value,

                                supportingText = {
                                    if (errorText.value){
                                        Text(
                                            text = "Обязятельно ввести",
                                            color = Color.Red
                                        )
                                    }
                                },

                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(modifier = Modifier.padding(16.dp)) {


                                TextButton(
                                    onClick = {
                                        if (eoState.value.isNotEmpty() && !errorText.value) {
                                            // Действия при успешном сохранении
                                            //viewModel.saveEoValue(eoState.value.toInt())
                                            openDialog.value = false
                                        } else {
                                            // Показываем ошибку, если поле пустое
                                            errorText.value = true
                                        }
                                    }
                                ) {
                                    Text("Сохранить")
                                }

                                /*
                                TextButton(
                                    onClick = { openDialog.value = false },
                                    // modifier = Modifier.align(Alignment.End)
                                ) {
                                    Text("Сохранить")
                                }*/


                                TextButton(
                                    onClick = { openDialog.value = false },
                                    //  modifier = Modifier.align(Alignment.End)
                                ) {
                                    Text("Отмена")
                                }
                            }

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
                    onClick = { openDialog.value = true  },
                ) {
                    Text(text = stringResource(R.string.uploading))
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {viewModel.addTrucks(trucks.size + 1, trucks.size + 1)},
                ) {
                    Text(text = stringResource(R.string._20_weight))
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {  },
                ) {
                    Text(text = stringResource(R.string._12_5_weight))
                }
            }
        }
    }
}

/*onClick = { viewModel.addTrucks(trucks.size + 1, trucks.size + 1  )},*/

