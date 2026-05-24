package com.pobezhkin.loadercalculator.presentation.screens.HistoryScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pobezhkin.loadercalculator.domain.model.ShiftHistoryModel
import com.pobezhkin.loadercalculator.domain.percent.calcPerformancePercent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ShiftHistoryCard(
    shift: ShiftHistoryModel,
    onDelete: () -> Unit
) {
    val percent = calcPerformancePercent(
        hoursWorked = shift.hoursWorked,
        palletsLoad20 = shift.totalLoadEo ?: 0,
        palletsLoadSmall = shift.totalMiniEo ?: 0,
        palletsReceive = shift.totalUploadEo ?: 0,
        palletsFreezeFromLoad = (shift.totalLoadFzEo ?: 0) + (shift.totalMiniFzEo ?: 0)
    )

    val dateText = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        .format(Date(shift.savedDate))

    val hoursText = if (shift.hoursWorked % 1.0 == 0.0)
        shift.hoursWorked.toInt().toString() else shift.hoursWorked.toString()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B2A4E))
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = dateText,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Удалить",
                        tint = Color(0xFFFF4F5A)
                    )
                }
            }
            Text(
                text = "Смена $hoursText часов",
                color = Color(0xFFB6BBD7),
                fontSize = 13.sp
            )
            Spacer(Modifier.height(8.dp))

            shift.totalLoadEo?.let { Text("Отгр.ЕО: $it", color = Color.White) }
            shift.totalLoadFzEo?.let { Text("Мороз 20T: $it", color = Color(0xFFB6BBD7)) }
            shift.totalMiniEo?.let { Text("Мини ЕО: $it", color = Color.White) }
            shift.totalMiniFzEo?.let { Text("Мороз мини: $it", color = Color(0xFFB6BBD7)) }
            shift.totalUploadEo?.let { Text("Принято ЕО: $it", color = Color.White) }

            Spacer(Modifier.height(8.dp))
            Text(
                text = "Сделано: ${"%.2f".format(percent)}%",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
