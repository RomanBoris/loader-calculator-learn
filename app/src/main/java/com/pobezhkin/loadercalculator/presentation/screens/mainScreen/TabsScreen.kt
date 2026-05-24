package com.pobezhkin.loadercalculator.presentation.screens.mainScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pobezhkin.loadercalculator.presentation.screens.HistoryScreen.HistoryShiftScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TabsScreen(
    shiftContent: @Composable () -> Unit = { ScreenAddCar() },
    historyContent: @Composable () -> Unit = { HistoryShiftScreen() }
) {

    val tabs = listOf("Смена", "История смен")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BluePalette.Background,
        contentWindowInsets = WindowInsets(0),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .statusBarsPadding()
                .fillMaxSize()
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = BluePalette.Background,
                contentColor = BluePalette.TextPrimary
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                        modifier = Modifier.height(44.dp),
                        text = { Text(text = title, fontSize = 16.sp) }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> shiftContent()
                    1 -> historyContent()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TabsScreenPreview() {
    TabsScreen(
        shiftContent = { Box(modifier = Modifier.fillMaxSize()) },
        historyContent = { Box(modifier = Modifier.fillMaxSize()) }
    )
}
