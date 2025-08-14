package com.pobezhkin.loadercalculator.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pobezhkin.loadercalculator.data.model.LoaderTruckEntity
import com.pobezhkin.loadercalculator.domain.model.LoaderTruckModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TruckItem(
    loadedTruckModel : LoaderTruckModel ,
    deleteElement: () -> Unit,
    onLongClick: () -> Unit
){

 Card (
        modifier = Modifier.fillMaxWidth()
            .padding(20.dp)
            .height(40.dp)
            .combinedClickable(
                onClick = {},
                onLongClick = onLongClick
            )
  ) {
        Row(
            modifier = Modifier.fillMaxWidth()
             .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ("Отгр.ЕО: ${loadedTruckModel.h_unit.toString()}"),
                fontSize = 24.sp,
            )

            Text(
                text = ("Мороз: ${loadedTruckModel.fz_h_unit.toString()}") ,
                fontSize = 24.sp
            )

            IconButton(onClick = deleteElement) {  // Кнопка удаления
                Icon(Icons.Default.Delete, contentDescription = "Удалить")
            }
        }
    }
}




/*@Preview
@Composable
fun PreviewTruckItem(){
    TruckItem()
}*/