package com.pobezhkin.loadercalculator.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pobezhkin.loadercalculator.R

@Composable
fun TruckPatternBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val truckSize = 36.dp.toPx()
            val spacing = 72.dp.toPx()

            // Рисуем грузовики в шахматном порядке
            for (row in 0..(size.height / spacing).toInt() + 1) {
                for (col in 0..(size.width / spacing).toInt() + 1) {
                    val offsetX = col * spacing + if (row % 2 == 1) spacing / 2 else 0f
                    val offsetY = row * spacing

                    if (offsetX < size.width + truckSize && offsetY < size.height + truckSize) {
                        translate(left = offsetX, top = offsetY) {
                            drawTruck(truckSize)
                        }
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawTruck(size: Float) {
    // Кабина грузовика
    drawRect(
        color = Color.White.copy(alpha = 0.08f),
        topLeft = Offset(0f, size * 0.3f),
        size = androidx.compose.ui.geometry.Size(size * 0.4f, size * 0.4f)
    )

    // Кузов грузовика
    drawRect(
        color = Color.White.copy(alpha = 0.08f),
        topLeft = Offset(size * 0.4f, size * 0.2f),
        size = androidx.compose.ui.geometry.Size(size * 0.6f, size * 0.6f)
    )

    // Переднее колесо
    drawCircle(
        color = Color.White.copy(alpha = 0.08f),
        center = Offset(size * 0.2f, size * 0.9f),
        radius = size * 0.1f
    )

    // Заднее колесо
    drawCircle(
        color = Color.White.copy(alpha = 0.08f),
        center = Offset(size * 0.7f, size * 0.9f),
        radius = size * 0.1f
    )

    // Фары (передние)
    drawCircle(
        color = Color.White.copy(alpha = 0.05f),
        center = Offset(size * 0.1f, size * 0.4f),
        radius = size * 0.05f
    )
}