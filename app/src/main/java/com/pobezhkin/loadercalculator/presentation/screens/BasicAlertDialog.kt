package com.pobezhkin.loadercalculator.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pobezhkin.loadercalculator.domain.model.WorkType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkAlertDialog(

    workType: WorkType,
    openDialog: Boolean,
    onDismiss : () -> Unit,
   onConfirm: (h_eo : Int, fz_h_eo: Int ) -> Unit

){

    val  eoWork = remember { mutableStateOf("") }
    val eoWorkFZ = remember { mutableStateOf("") }

    val errorText = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
   // val openDialog = remember{ mutableStateOf(false) }


    if (openDialog ) {
        BasicAlertDialog(
            onDismissRequest = onDismiss
        ) {
            Surface(
                modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        text = when (workType) {
                            WorkType.LOADING -> "Погрузкка"
                            WorkType.UPLOADING -> "Введите количество \n" +
                                    " принятых из под стола ЕО:"
                        },
                        style = MaterialTheme.typography.titleMedium
                    )

                    //проверяем
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        value = eoWork.value, // присваивание тут
                        onValueChange = { newText ->
                            if (newText.all { it.isDigit() } || newText.isEmpty()) {
                                eoWork.value = newText
                                errorText.value = newText.isEmpty()
                            }
                        },

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),

                        label = { Text("ЕО:") },

                        singleLine = true,

                        isError = errorText.value,

                        supportingText = {
                            if (errorText.value) {
                                Text(
                                    text = "Обязятельно ввести",
                                    color = Color.Red
                                )
                            }
                        },

                        )

                    if (workType == WorkType.LOADING) {
                        Spacer(modifier = Modifier.height(20.dp))
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = eoWorkFZ.value,

                            onValueChange = { text ->
                                if (text.all { it.isDigit() } || text.isEmpty()) {
                                    eoWorkFZ.value = text
                                }
                            },
                            label = { Text("Заморозка (необязательно)") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )

                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(modifier = Modifier.padding(16.dp)) {
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {

                        if (eoWork.value.isNotEmpty()) {
                            onConfirm(
                                eoWork.value.toInt(),
                                eoWork.value.toInt()
                            )
                            onDismiss()
                        }

                        else {
                        errorText.value = true

                    }
                    }
                    ) {
                    Text("Сохранить")
                }


                    TextButton(onClick = onDismiss) {
                        Text("Отмена")
                    }

                }


            }
        }
    }


}
