package com.pobezhkin.loadercalculator.presentation.screens.mainScreen.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun HoursEditDialog(
    currentHours: Double,
    onDismiss: () -> Unit,
    onConfirm: (Double) -> Unit
) {
    var input by remember {
        val text = if (currentHours % 1.0 == 0.0) currentHours.toInt().toString()
                   else currentHours.toString()
        mutableStateOf(text)
    }
    val parsed = input.toDoubleOrNull()
    val isValid = parsed != null && parsed > 0.0

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Длительность смены") },
        text = {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Часов *") },
                isError = !isValid && input.isNotEmpty(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = { if (isValid) onConfirm(parsed!!) },
                enabled = isValid
            ) { Text("Сохранить") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Отмена") }
        }
    )
}
