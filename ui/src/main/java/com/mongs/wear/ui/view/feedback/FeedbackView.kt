package com.mongs.wear.ui.view.feedback

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.mongs.wear.ui.global.component.background.FeedbackBackground

@Composable
fun FeedbackView(
    navController: NavController,
) {
    Box {
        FeedbackBackground()
        FeedbackContent(modifier = Modifier.zIndex(1f))
    }
}

@Composable
private fun FeedbackContent(
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {

    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun FeedbackViewPreview() {
    Box {
        FeedbackBackground()
        FeedbackContent(
            modifier = Modifier.zIndex(1f)
        )
    }
}