package com.mongs.wear.presentation.component.dialog.help

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
import com.mongs.wear.presentation.global.component.common.PageIndicator
import com.mongs.wear.presentation.global.dialog.help.content.HelpCancelContent
import com.mongs.wear.presentation.global.dialog.help.content.slot.HelpAddSlotContent
import com.mongs.wear.presentation.global.dialog.help.content.slot.HelpBuySlotContent
import com.mongs.wear.presentation.global.dialog.help.content.slot.HelpDeleteSlotContent
import com.mongs.wear.presentation.global.dialog.help.content.slot.HelpDetailSlotContent
import com.mongs.wear.presentation.global.dialog.help.content.slot.HelpGraduateSlotContent
import com.mongs.wear.presentation.global.dialog.help.content.slot.HelpPickSlotContent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HelpSlotDialog(
    cancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = 0) { 7 }

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
                        HelpAddSlotContent()
                    }

                    1 -> {
                        HelpPickSlotContent()
                    }

                    2 -> {
                        HelpDeleteSlotContent()
                    }

                    3 -> {
                        HelpGraduateSlotContent()
                    }

                    4 -> {
                        HelpBuySlotContent()
                    }

                    5 -> {
                        HelpDetailSlotContent()
                    }

                    6 -> {
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