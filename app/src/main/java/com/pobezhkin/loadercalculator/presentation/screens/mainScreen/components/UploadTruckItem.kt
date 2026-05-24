package com.pobezhkin.loadercalculator.presentation.screens.mainScreen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pobezhkin.loadercalculator.domain.model.UploadTruckModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UploadTruckItem(
    upLoaderTruckModel: UploadTruckModel,
    deleteElement: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Палитра в стиле «погодного» item
    val cardStart = Color(0xFF2A3763)
    val cardEnd = Color(0xFF374272)
    val textPrimary = Color(0xFFEAEAF7)
    val textSecondary = Color(0xFFB6BBD7)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.horizontalGradient(listOf(cardStart, cardEnd)))
            .combinedClickable(
                onClick = {},
                onLongClick = onLongClick
            )
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Принято ЕО: ${upLoaderTruckModel.upload}",
            style = MaterialTheme.typography.titleMedium,
            color = textPrimary
        )

        IconButton(
            onClick = deleteElement,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Удалить",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}
