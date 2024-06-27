package com.mongs.wear.ui.view.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.mongs.wear.ui.global.component.background.ServerErrorBackground
import com.mongs.wear.ui.viewModel.main.MainViewModel


@Composable
fun MainView (
    closeApp: () -> Unit,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val networkFlag = mainViewModel.networkFlag.observeAsState(true)

    if (networkFlag.value) {
        NavContent()
    } else {
        ServerErrorBackground()
        ServerErrorContent(
            closeApp = closeApp,
            modifier = Modifier.zIndex(1f)
        )
    }
}