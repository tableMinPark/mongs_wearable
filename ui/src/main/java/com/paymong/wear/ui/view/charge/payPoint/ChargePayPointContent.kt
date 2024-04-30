package com.paymong.wear.ui.view.charge.payPoint

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.HorizontalPageIndicator
import androidx.wear.compose.material.PageIndicatorState
import com.paymong.wear.ui.global.component.StarPoint
import com.paymong.wear.ui.global.dialog.ConfirmDialog
import com.paymong.wear.ui.global.theme.PaymongNavy
import com.paymong.wear.ui.view.charge.component.ChargePayPoint

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChargePayPointContent() {
    val isChargePayPointDialogShow = remember{ mutableStateOf(false) }
    /** 페이지 표시기 **/
    val pagerState = rememberPagerState(initialPage = 0) { 1 }
    val pageIndicatorState: PageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageOffset: Float
                get() = pagerState.currentPageOffsetFraction
            override val selectedPage: Int
                get() = pagerState.currentPage
            override val pageCount: Int
                get() = 1
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
            .padding(top = 10.dp, bottom = 20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
            ) {
                StarPoint(starPoint = 0)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
            ) {
                HorizontalPager(
                    state = pagerState,
                ) { page: Int ->
                    for (idx: Int in 0 until 1) {
                        if (page == idx) {
                            ChargePayPoint(
                                price = 1000,
                                chargePoint = idx,
                                isChargePayPointEnable = { isChargePayPointDialogShow.value = true }
                            )
                        }
                    }
                }
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

    if (isChargePayPointDialogShow.value) {
        ConfirmDialog(
            text = "페이 포인트를\n충전 하시겠습니까?",
            confirm = { Log.d("test", "charge pay point") },
            cancel = { isChargePayPointDialogShow.value = false }
        )
    }
}