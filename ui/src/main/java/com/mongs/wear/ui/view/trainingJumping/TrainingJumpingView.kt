package com.mongs.wear.ui.view.trainingJumping

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.mongs.wear.ui.global.component.background.FeedNestedBackground
import com.mongs.wear.ui.global.component.background.TrainingNestedBackground
import com.mongs.wear.ui.global.resource.NavItem
import com.mongs.wear.ui.global.theme.DAL_MU_RI
import com.mongs.wear.ui.global.theme.PaymongWhite

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

