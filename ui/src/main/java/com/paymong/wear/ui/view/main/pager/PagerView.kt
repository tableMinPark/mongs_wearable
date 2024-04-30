package com.paymong.wear.ui.view.main.pager

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.DefaultValue
import com.paymong.wear.domain.processCode.MainProcessCode
import com.paymong.wear.domain.viewModel.main.MainViewModel
import com.paymong.wear.ui.global.component.MainBackground
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerView(
    navController: NavController,
    scrollPage: (Int) -> Unit,
    pagerState: PagerState,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    /** Process Code **/
    val processCode = mainViewModel.processCode.observeAsState(MainProcessCode.STAND_BY)
    /** Observe **/
    val backgroundMapCode = mainViewModel.backgroundMapCode.observeAsState(DefaultValue.BACKGROUND_MAP_CODE)
    val starPoint = mainViewModel.starPoint.observeAsState(DefaultValue.STAR_POINT)
    val slotVo = mainViewModel.slotVo.observeAsState(DefaultValue.SLOT_VO)
    /** UI Flag **/
    val isLoadingBarShow = remember{ mutableStateOf(true) }
    val isSlotLoad = remember{ mutableStateOf(false) }

    when (processCode.value) {
        MainProcessCode.LOAD_SLOT_END -> {
            LaunchedEffect(Unit) {
                delay(DefaultValue.LOAD_DELAY)
                isLoadingBarShow.value = false
                isSlotLoad.value = true
                mainViewModel.resetProcessCode()
            }
        }
        else -> {}
    }

    Box {
        /** Background **/
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            MainBackground(backgroundMapCode.value)
        }

        PagerContent(
            slotVo = slotVo,
            starPoint = starPoint,
            navController = navController,
            pagerState = pagerState,
            scrollPage = scrollPage,
            isLoadingBarShow = isLoadingBarShow,
            isSlotLoad = isSlotLoad
        )
    }

    LaunchedEffect(Unit) {
        mainViewModel.loadSlot()
    }
}
