package com.mongs.wear.presentation.pages.main.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mongs.wear.domain.management.vo.MongVo
import com.mongs.wear.presentation.assets.MapResourceCode
import com.mongs.wear.presentation.component.common.background.MainPagerBackground
import com.mongs.wear.presentation.component.common.bar.LoadingBar
import com.mongs.wear.presentation.component.common.pagenation.PageIndicator
import com.mongs.wear.presentation.pages.main.condition.MainConditionView
import com.mongs.wear.presentation.pages.main.configure.MainConfigureView
import com.mongs.wear.presentation.pages.main.interaction.MainInteractionView
import com.mongs.wear.presentation.pages.main.slot.MainSlotView
import com.mongs.wear.presentation.pages.main.walking.MainWalkingView

@Composable
fun MainPagerView(
    navController: NavController,
    pagerState: PagerState,
    pagerScroll: (Int) -> Unit,
    mainPagerViewModel: MainPagerViewModel = hiltViewModel()
) {
    val mongVo = mainPagerViewModel.mongVo.observeAsState()
    val backgroundMapCodeState = mainPagerViewModel.backgroundMapCode.observeAsState(MapResourceCode.MP000.name)

    Box {
        if (mainPagerViewModel.uiState.loadingBar) {
            MainPagerBackground()
            MainPagerLoadingBar(modifier = Modifier.zIndex(1f))
        } else {
            MainPagerBackground(
                mapCode = backgroundMapCodeState.value,
                pagerState = pagerState,
            )
            PageIndicator(
                pagerState = pagerState,
                modifier = Modifier.zIndex(1f),
            )
            NormalMainPagerContent(
                navController = navController,
                mongVo = mongVo.value ?: MongVo(),
                pagerState = pagerState,
                scrollPage = pagerScroll,
                modifier = Modifier.zIndex(2f),
            )
        }
    }
}

@Composable
private fun NormalMainPagerContent(
    navController: NavController,
    mongVo: MongVo,
    pagerState: PagerState,
    scrollPage: (Int) -> Unit,
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
                0 -> MainWalkingView(
                    mongVo = mongVo,
                )

                1 -> MainConditionView(
                    mongVo = mongVo,
                    isPageChanging = isPageChanging,
                )

                2 -> MainSlotView(
                    navController = navController,
                    mongVo = mongVo,
                    isPageChanging = isPageChanging,
                )

                3 -> MainInteractionView(
                    navController = navController,
                    mongVo = mongVo,
                    scrollPage = scrollPage,
                )

                4 -> MainConfigureView(
                    navController = navController,
                    scrollPage = scrollPage,
                )
            }
        }
    }
}

/**
 * LoadingBar Component
 */
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