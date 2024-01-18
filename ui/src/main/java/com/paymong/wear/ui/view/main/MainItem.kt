package com.paymong.wear.ui.view.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Icon
import com.paymong.wear.ui.view.common.background.Process

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Film(
    pagerState: PagerState,
    zIndex: State<Float>,
    backgroundAlpha: State<Float>
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .zIndex(zIndex.value)
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = backgroundAlpha.value))
    ) {
        if (zIndex.value == 1f && pagerState.currentPageOffsetFraction == 0f) {
            Icon(
                imageVector = Icons.Outlined.Lock, contentDescription = null,
                modifier = Modifier
                    .alpha(alpha = 0.8f)
                    .size(70.dp)
            )
        }
    }
}

@Composable
fun MainProcess() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Process(processSize = 30)
    }
}
