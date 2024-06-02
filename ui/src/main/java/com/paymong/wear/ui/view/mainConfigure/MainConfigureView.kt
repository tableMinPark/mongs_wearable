package com.paymong.wear.ui.view.mainConfigure

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.ui.viewModel.mainConfigure.MainConfigureViewModel

@Composable
fun MainConfigureView(
    navController: NavController,
    scrollPage: (Int) -> Unit,
    mainConfigureViewModel: MainConfigureViewModel = hiltViewModel(),
) {
    Box {
        MainConfigureContent(modifier = Modifier.zIndex(1f))
    }
}

@Composable
private fun MainConfigureContent(
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {

    }
}