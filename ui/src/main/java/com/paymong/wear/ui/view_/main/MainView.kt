package com.paymong.wear.ui.view_.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.wear.compose.material.HorizontalPageIndicator
import androidx.wear.compose.material.PageIndicatorState
import com.paymong.wear.ui.theme.PaymongNavy
import com.paymong.wear.ui.view_.common.MainBackground
import kotlin.math.absoluteValue

@Composable
fun MainView(
    navController: NavController,
) {

    Box {
        /** Background **/
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            MainBackground()
        }

        MainContent()
    }
}

private const val PAGE_LENGTH = 4
private val pageBrightness = arrayOf(0.4f, 0f, 0.4f, 0.4f)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainContent() {
    /** 페이지 상태 **/
    val pagerState = rememberPagerState(initialPage = 1) { PAGE_LENGTH }

    /** 페이지 표시기 **/
    val pageIndicatorState: PageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageOffset: Float
                get() = pagerState.currentPageOffsetFraction
            override val selectedPage: Int
                get() = pagerState.currentPage
            override val pageCount: Int
                get() = PAGE_LENGTH
        }
    }

    /** 흐린 배경 **/
    val backgroundAlpha = remember {
        derivedStateOf {
            val currentPage = pagerState.currentPage
            val ratio = pagerState.currentPageOffsetFraction.coerceIn(-1f, 1f)
            val nextPage = if (ratio < 0) {
                currentPage - 1
            } else if (ratio > 0) {
                currentPage + 1
            } else currentPage

            val current = pageBrightness[currentPage]
            val next = pageBrightness[nextPage]
            current + (next - current) * ratio.absoluteValue
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(0.1f)
            .background(color = Color.Black.copy(alpha = backgroundAlpha.value))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {}
            )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
    ) {
        HorizontalPager(
            state = pagerState,
        ) {page: Int ->
            when (page) {
                0 -> MainConditionView()
                1 -> MainSlotView()
                2 -> MainInteractionView()
                3 -> MainConfigureView()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(2f)
    ) {
        HorizontalPageIndicator(
            pageIndicatorState = pageIndicatorState,
            selectedColor = PaymongNavy,
            unselectedColor = Color.White,
            indicatorSize = 6.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 5.dp)
        )
    }
}