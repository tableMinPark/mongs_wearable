package com.paymong.wear.ui.view.main.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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
import com.paymong.wear.domain.repository.slot.vo.SlotVo
import com.paymong.wear.ui.global.theme.PaymongNavy
import com.paymong.wear.ui.global.component.LoadingBar
import com.paymong.wear.ui.view.main.condition.MainConditionView
import com.paymong.wear.ui.view.main.configure.MainConfigureView
import com.paymong.wear.ui.view.main.interaction.MainInteractionView
import com.paymong.wear.ui.view.main.slot.MainSlotView
import kotlin.math.absoluteValue


const val PAGE_LENGTH = 4
private val pageBrightness = arrayOf(
    0.4f,                   // MainConditionView
    0.0f,                   // MainSlotView
    0.4f,                   // MainInteractionView
    0.4f                    // MainConfigureView
)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerContent(
    slotVo: State<SlotVo>,
    starPoint: State<Int>,
    navController: NavController,
    pagerState: PagerState,
    scrollPage: (Int) -> Unit,
    isLoadingBarShow: State<Boolean>,
    isSlotLoad: State<Boolean>
) {
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
    /** 화면 이동 감지 **/
    val isPageChange = remember {
        derivedStateOf {
            val currentPage = pagerState.currentPage
            val ratio = pagerState.currentPageOffsetFraction.coerceIn(-1f, 1f)
            val nextPage = if (ratio < 0) {
                currentPage - 1
            } else if (ratio > 0) {
                currentPage + 1
            } else currentPage

            currentPage != nextPage
        }
    }

    if (isSlotLoad.value) {
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
            ) { page: Int ->
                when (page) {
                    0 -> MainConditionView(
                        slotVo = slotVo,
                        isPageChange = isPageChange,
                    )

                    1 -> MainSlotView(
                        slotVo = slotVo,
                        isPageChange = isPageChange,
                        scrollPage = scrollPage,
                        navController = navController
                    )

                    2 -> MainInteractionView(
                        slotVo = slotVo,
                        scrollPage = scrollPage,
                        navController = navController
                    )

                    3 -> MainConfigureView(
                        scrollPage = scrollPage,
                        navController = navController
                    )
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

    /** 로딩 스피너 표출 (상점 아이템 리스트 로딩 시) **/
    if (isLoadingBarShow.value) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color = Color.Black.copy(alpha = 0.4f))
                .fillMaxSize()
                .zIndex(3f)
        ) {
            LoadingBar(size = 50)
        }
    }
}
