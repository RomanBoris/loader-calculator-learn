package com.pobezhkin.loadercalculator.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.pobezhkin.loadercalculator.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenAddCar(){
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
             modifier =  Modifier.padding(innerPadding)
         ) {
             Text("Добавь машину")
         }
         }


}

@Preview
@Composable
fun PreviewScreenAddCar(){
    ScreenAddCar()
}