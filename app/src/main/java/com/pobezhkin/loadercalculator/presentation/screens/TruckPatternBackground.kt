package com.pobezhkin.loadercalculator.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pobezhkin.loadercalculator.R

@Composable
fun TruckPatternBackground() {
    // Размер ячейки сетки (расстояние между грузовиками)
    val spacing = 72.dp
    val crossAxisSize = 5 // Примерно 5 грузовиков по ширине

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp) // Лёгкий отступ, чтобы не прилипали к краям
    ) {
        // Сетка из грузовиков
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(spacing)
        ) {
            repeat(15) { row -> // 15 строк (хватит для любого экрана)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(spacing),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    repeat(crossAxisSize) { col ->
                        Image(
                            painter = painterResource( R.drawable.ic_truck),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(28.dp)
                                .alpha(0.08f), // Очень слабая видимость — как в чате
                            colorFilter = ColorFilter.tint(
                                androidx.compose.ui.graphics.Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}