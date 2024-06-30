package com.mongs.wear.ui.view.trainingJumping

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.mongs.wear.ui.global.component.background.TrainingNestedBackground

@Composable
fun TrainingJumpingView(
    navController: NavController,
) {
    Box {
        TrainingNestedBackground()
        TrainingJumpingContent(
            modifier = Modifier.zIndex(1f),
        )
    }
}

@Composable
private fun TrainingJumpingContent(
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
private fun FeedMenuViewPreview() {
    Box {
        TrainingNestedBackground()
        TrainingJumpingContent(
            modifier = Modifier.zIndex(1f),
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun LargeFeedMenuViewPreview() {
    Box {
        TrainingNestedBackground()
        TrainingJumpingContent(
            modifier = Modifier.zIndex(1f),
        )
    }
}

