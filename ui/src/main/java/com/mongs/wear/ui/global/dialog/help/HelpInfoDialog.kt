package com.mongs.wear.ui.global.dialog.help

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.mongs.wear.ui.global.component.common.PageIndicator
import com.mongs.wear.ui.global.dialog.help.content.HelpCancelContent
import com.mongs.wear.ui.global.dialog.help.content.info.HelpEvolutionInfoContent
import com.mongs.wear.ui.global.dialog.help.content.info.HelpCharacterInfoContent
import com.mongs.wear.ui.global.dialog.help.content.info.HelpCollectionInfoContent
import com.mongs.wear.ui.global.dialog.help.content.info.HelpWalkingRewardInfoContent
import com.mongs.wear.ui.global.dialog.help.content.point.HelpSubStarPointContent
import com.mongs.wear.ui.global.dialog.help.content.point.HelpWarningPointContent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HelpInfoDialog(
    cancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = 0) { 5 }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(color = Color.Black.copy(alpha = 0.85f))
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = cancel,
            )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
        ) {
            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> {
                        HelpCharacterInfoContent()
                    }

                    1 -> {
                        HelpCollectionInfoContent()
                    }

                    2 -> {
                        HelpEvolutionInfoContent()
                    }

                    3 -> {
                        HelpWalkingRewardInfoContent()
                    }

                    4 -> {
                        HelpCancelContent(cancel = cancel)
                    }
                }
            }
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
        ) {
            PageIndicator(pagerState = pagerState)
        }
    }
}
