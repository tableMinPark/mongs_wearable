package com.mongs.wear.ui.view.battleMatch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mongs.wear.ui.global.component.background.BattleNestedBackground
import com.mongs.wear.ui.viewModel.battleMatch.BattleMatchViewModel

@Composable
fun BattleMatchView(
    navController: NavController,
    battleMatchViewModel: BattleMatchViewModel = hiltViewModel(),
) {
    Box {
        BattleNestedBackground()
        BattleMatchContent(modifier = Modifier.zIndex(1f))
    }
}

@Composable
private fun BattleMatchContent(
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun FeedMenuViewPreview() {
    Box {
        BattleNestedBackground()
        BattleMatchContent(modifier = Modifier.zIndex(1f))
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun LargeFeedMenuViewPreview() {
    Box {
        BattleNestedBackground()
        BattleMatchContent(modifier = Modifier.zIndex(1f))
    }
}

