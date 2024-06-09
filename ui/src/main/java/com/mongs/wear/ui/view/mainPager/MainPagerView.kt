package com.mongs.wear.ui.view.mainPager

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.mongs.wear.domain.vo.SlotVo
import com.mongs.wear.ui.global.component.background.MainPagerBackground
import com.mongs.wear.ui.global.component.common.LoadingBar
import com.mongs.wear.ui.global.component.common.PageIndicator
import com.mongs.wear.ui.global.resource.MapResourceCode
import com.mongs.wear.ui.view.mainCondition.MainConditionView
import com.mongs.wear.ui.view.mainConfigure.MainConfigureView
import com.mongs.wear.ui.view.mainInteraction.MainInteractionView
import com.mongs.wear.ui.view.mainSlot.MainSlotView
import com.mongs.wear.ui.view.mainWalking.MainWalkingView
import com.mongs.wear.ui.viewModel.mainPager.MainPagerViewModel
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

private val pageBrightness = arrayOf(0.4f, 0.4f, 0.0f, 0.4f, 0.4f)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainPagerView(
    navController: NavController,
    scrollPage: (Int) -> Unit,
    pagerState: PagerState,
    mainPagerViewModel: MainPagerViewModel = hiltViewModel()
) {
    Box {
        if (mainPagerViewModel.uiState.loadingBar) {
            MainPagerBackground()
            MainPagerLoadingBar(modifier = Modifier.zIndex(1f))
        } else {
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
            val slotVo = mainPagerViewModel.slotVo.observeAsState(SlotVo())
            val backgroundMapCode = mainPagerViewModel.backgroundMapCode.observeAsState(MapResourceCode.MP000.name)

            MainPagerBackground(
                mapCode = backgroundMapCode.value,
                backgroundAlpha = backgroundAlpha.value,
            )
            PageIndicator(
                pagerState = pagerState,
                modifier = Modifier.zIndex(1f),
            )
            MainPagerContent(
                navController = navController,
                scrollPage = scrollPage,
                slotVo = slotVo,
                pagerState = pagerState,
                modifier = Modifier.zIndex(2f),
            )
        }
    }

    LaunchedEffect(Unit) {
        mainPagerViewModel.loadData()
    }
}

@Composable
private fun MainPagerLoadingBar(
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        LoadingBar()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainPagerContent(
    navController: NavController,
    scrollPage: (Int) -> Unit,
    slotVo: State<SlotVo>,
    pagerState: PagerState,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    val isPageChanging = remember {
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


    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        HorizontalPager(state = pagerState) { page: Int ->
            when (page) {
                0 -> MainWalkingView()
                1 -> MainConditionView(
                    slotVo = slotVo,
                    isPageChanging = isPageChanging,
                )

                2 -> MainSlotView(
                    navController = navController,
                    scrollPage = scrollPage,
                    slotVo = slotVo,
                    isPageChanging = isPageChanging,
                )

                3 -> MainInteractionView(
                    navController = navController,
                    scrollPage = scrollPage,
                    slotVo = slotVo,
                )

                4 -> MainConfigureView(
                    navController = navController,
                    scrollPage = scrollPage,
                )
            }
        }
    }
}