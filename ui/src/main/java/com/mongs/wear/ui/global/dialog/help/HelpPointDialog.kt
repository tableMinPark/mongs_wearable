package com.mongs.wear.ui.global.dialog.help

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.mongs.wear.ui.global.component.common.PageIndicator
import com.mongs.wear.ui.global.dialog.help.content.point.HelpAddPayPointContent
import com.mongs.wear.ui.global.dialog.help.content.point.HelpAddStarPointContent
import com.mongs.wear.ui.global.dialog.help.content.HelpCancelContent
import com.mongs.wear.ui.global.dialog.help.content.point.HelpSubPayPointContent
import com.mongs.wear.ui.global.dialog.help.content.point.HelpSubStarPointContent
import com.mongs.wear.ui.global.dialog.help.content.point.HelpWarningPointContent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HelpPointDialog(
    cancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = 0) { 6 }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(color = Color.Black.copy(alpha = 0.85f))
            .fillMaxSize()
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
                        HelpAddPayPointContent()
                    }

                    1 -> {
                        HelpSubPayPointContent()
                    }

                    2 -> {
                        HelpAddStarPointContent()
                    }

                    3 -> {
                        HelpSubStarPointContent()
                    }

                    4 -> {
                        HelpWarningPointContent()
                    }

                    5 -> {
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
