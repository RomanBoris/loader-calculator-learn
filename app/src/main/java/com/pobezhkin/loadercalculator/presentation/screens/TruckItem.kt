package com.pobezhkin.loadercalculator.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TruckItem(){
OutlinedCard (
colors = CardDefaults.cardColors(
    containerColor = MaterialTheme.colorScheme.surface,
),
    border = BorderStroke(1.dp, Color.Black ),
    modifier = Modifier.size(width = 0.dp, height = 100.dp)

  ) {

 }
}




@Preview
@Composable
fun PreviewTruckItem(){
    TruckItem()
}