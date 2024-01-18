package com.paymong.wear.ui.view_.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.viewModel_.main.MainSlotViewModel

@Composable
fun MainSlotView(
    navController: NavController,
    mainSlotViewModel: MainSlotViewModel = hiltViewModel()
) {
    
    Box {
        MainSlotViewContent()
    }
}

@Composable
fun MainSlotViewContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {

    }
}