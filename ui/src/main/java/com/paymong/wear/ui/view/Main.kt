package com.paymong.wear.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.paymong.wear.domain.viewModel.MainViewModel
import com.paymong.wear.ui.code.MapCode
import com.paymong.wear.ui.code.MongCode
import com.paymong.wear.ui.code.StateCode
import com.paymong.wear.ui.theme.PaymongNavy
import com.paymong.wear.ui.view.common.MainBackground
import com.paymong.wear.ui.view.condition.ConditionView
import com.paymong.wear.ui.view.figure.FigureView
import com.paymong.wear.ui.view.infomation.InformationView
import com.paymong.wear.ui.view.interaction.InteractionView

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Main(
    pagerState: PagerState,
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    MainBackground(
        mapCode = MapCode.valueOf(mainViewModel.mapCode),
        page = pagerState.currentPage,
        mongCode = MongCode.valueOf(mainViewModel.mongCode),
        stateCode = StateCode.valueOf(mainViewModel.stateCode)
    )

    Column {
        HorizontalPager(count = 4, state = pagerState, modifier = Modifier.weight(1f)) {
            page: Int -> when(page) {
                0 -> ConditionView()
                1 -> FigureView()
                2 -> InteractionView(navController = navController)
                3 -> InformationView()
            }
        }

        HorizontalPagerIndicator(
            activeColor = PaymongNavy,
            inactiveColor = Color.White,
            indicatorWidth = 6.dp,
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 7.dp)
        )
    }
}