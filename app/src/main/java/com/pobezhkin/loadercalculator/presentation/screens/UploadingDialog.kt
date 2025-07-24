package com.pobezhkin.loadercalculator.presentation.screens

import android.widget.PopupMenu.OnDismissListener
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadingTrack(

    openDialog: Boolean,
    initialEo: Int = 0,
    onDismiss: () -> Unit,
    onConfirm: (uploadEo: Int) -> Unit

){

    val eoValue = remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val isEoError = remember { mutableStateOf(false) }

    if (openDialog) {
        LaunchedEffect (Unit){
            eoValue.value = initialEo.toString()
            focusRequester.requestFocus()
        }
    }

    if(openDialog){
        BasicAlertDialog(
            onDismissRequest = onDismiss
        ) {
            Surface(
                modifier = Modifier
                    .width(280.dp)
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = if (initialEo == 0) "Добавление ЕО" else "Редактирование ЕО",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Поле ЕО (обязательное)
                    TextField(modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                        value = eoValue.value,
                        onValueChange = { text ->
                            if (text.all { it.isDigit() }) {
                                eoValue.value = text
                                isEoError.value = false
                            }
                        },
                        label = { Text("Количество ЕО*") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = isEoError.value,
                        supportingText = {
                            if (isEoError.value) {
                                Text("Обязательное поле", color = Color.Red)
                            }
                        })



                    Spacer(modifier = Modifier.height(24.dp))

                    // Кнопки (выровнены по правому краю)
                    Row(
                        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text("Отмена")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(onClick = {
                            if (eoValue.value.isEmpty()) {
                                isEoError.value = true
                            } else {
                                onConfirm(
                                    eoValue.value.toInt(),

                                )
                                onDismiss()
                            }
                        }) {
                            Text("Сохранить")
                        }
                    }
                }



            }

        }
    }



}