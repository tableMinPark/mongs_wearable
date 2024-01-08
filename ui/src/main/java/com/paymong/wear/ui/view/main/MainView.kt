package com.paymong.wear.ui.view.main

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.HorizontalPageIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.PageIndicatorState
import com.paymong.wear.domain.viewModel.DefaultValue
import com.paymong.wear.domain.viewModel.code.FeedSelectCode
import com.paymong.wear.domain.viewModel.code.MainCode
import com.paymong.wear.domain.viewModel.main.MainViewModel
import com.paymong.wear.ui.code.MapCode
import com.paymong.wear.ui.code.MongCode
import com.paymong.wear.ui.code.StateCode
import com.paymong.wear.ui.theme.PaymongNavy
import com.paymong.wear.ui.view.common.background.Background
import com.paymong.wear.ui.view.common.background.Process
import com.paymong.wear.ui.view.main.condition.ConditionView
import com.paymong.wear.ui.view.main.configure.ConfigureView
import com.paymong.wear.ui.view.main.interaction.InteractionView
import com.paymong.wear.ui.view.main.slot.SlotView
import com.paymong.wear.ui.view.main.slotAction.SlotActionView
import kotlin.math.absoluteValue

const val maxPage = 4
val pageBrightness = listOf(0.4f, 0f, 0.4f, 0f)

@Composable
fun MainView(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    /** Flag **/
    val processCode = mainViewModel.processCode.observeAsState(MainCode.LOAD)

    /** Observer **/
    val mapCode = mainViewModel.mapCode.observeAsState(DefaultValue.mapCode)
    val mongCode = mainViewModel.mongCode.observeAsState(DefaultValue.mongCode)
    val stateCode = mainViewModel.stateCode.observeAsState(DefaultValue.stateCode)
    val poopCount = mainViewModel.poopCount.observeAsState(DefaultValue.poopCount)

    val health = mainViewModel.health.observeAsState(DefaultValue.health)
    val satiety = mainViewModel.health.observeAsState(DefaultValue.satiety)
    val strength = mainViewModel.health.observeAsState(DefaultValue.strength)
    val sleep = mainViewModel.health.observeAsState(DefaultValue.sleep)

    /** Data **/
    val map = MapCode.valueOf(mapCode.value)

    /** LaunchedEffect **/
    LaunchedEffect(Unit) {}

    /** Background **/
    Background(map = map)

    /** Logic by ProcessCode **/
    when(processCode.value) {
        MainCode.LOAD -> {
            MainProcess()
        }
        else -> {
            /** Content **/
            MainContent(
                mongCode = mongCode,
                stateCode = stateCode,
                poopCount = poopCount,
                health = health,
                satiety = satiety,
                strength = strength,
                sleep = sleep,
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainContent(
    mongCode: State<String>,
    stateCode: State<String>,
    poopCount: State<Int>,
    health: State<Float>,
    satiety: State<Float>,
    strength: State<Float>,
    sleep: State<Float>,
    navController: NavController
) {
    /** Data **/
    val mong = MongCode.valueOf(mongCode.value)

    /** State **/
    val pagerState = rememberPagerState(initialPage = 1) { maxPage }
    val pageIndicatorState: PageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageOffset: Float
                get() = pagerState.currentPageOffsetFraction
            override val selectedPage: Int
                get() = pagerState.currentPage
            override val pageCount: Int
                get() = maxPage
        }
    }
    val animateSlotAction = remember { mutableStateOf(false) }
    val zIndex = remember { mutableFloatStateOf(-1f) }

    /** State Refresh **/
    val backgroundAlpha = remember {
        derivedStateOf {
            val currentPage = pagerState.currentPage
            val ratio = pagerState.currentPageOffsetFraction.coerceIn(-1f, 1f)
            val nextPage = if (ratio < 0) {
                currentPage - 1
            } else if (ratio > 0){
                currentPage + 1
            } else currentPage

            val current = pageBrightness[currentPage]
            val next = pageBrightness[nextPage]
            current + (next - current) * ratio.absoluteValue
        }
    }
    if(pagerState.currentPage == 0 && mong == MongCode.CH444) {
        zIndex.floatValue = 1f
    } else {
        zIndex.floatValue = -1f
    }

    /** Fun **/
    val showSlotActionView = {
        animateSlotAction.value = true
    }
    val hideSlotActionView = {
        animateSlotAction.value = false
    }

    /** View Content **/
    Box{
        HorizontalPager(
            modifier = Modifier.zIndex(0f),
            state = pagerState,
        ) { page: Int ->
            when (page) {
                0 -> ConditionView(
                    health = health,
                    satiety = satiety,
                    strength = strength,
                    sleep = sleep
                )

                1 -> SlotView(
                    navController = navController,
                    showActionContent = showSlotActionView,
                    mongCode = mongCode,
                    stateCode = stateCode,
                    poopCount = poopCount
                )

                2 -> InteractionView(navController = navController, stateCode = stateCode)
                3 -> ConfigureView(navController = navController)
            }
        }
        Film(pagerState = pagerState, zIndex = zIndex, backgroundAlpha = backgroundAlpha)
        HorizontalPageIndicator(
            pageIndicatorState = pageIndicatorState,
            selectedColor = PaymongNavy,
            unselectedColor = Color.White,
            indicatorSize = 6.dp,
            modifier = Modifier
                .zIndex(2f)
                .align(Alignment.BottomCenter)
                .padding(bottom = 7.dp)
        )
        Box(
            modifier = Modifier.zIndex(3f)
        ) {
            SlotActionView(
                navController = navController,
                animateSlotAction = animateSlotAction,
                hideSlotActionView = hideSlotActionView,
                stateCode = stateCode
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Film(
    pagerState: PagerState,
    zIndex: State<Float>,
    backgroundAlpha: State<Float>
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .zIndex(zIndex.value)
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = backgroundAlpha.value))
    ) {
        if (zIndex.value == 1f && pagerState.currentPageOffsetFraction == 0f) {
            Icon(
                imageVector = Icons.Outlined.Lock, contentDescription = null,
                modifier = Modifier
                    .alpha(alpha = 0.8f)
                    .size(70.dp)
            )
        }
    }
}

@Composable
fun MainProcess() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Process(processSize = 30)
    }
}
