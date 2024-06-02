package com.paymong.wear.ui.view.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.wear.ui.global.resource.NavItem
import com.paymong.wear.ui.view.login.LoginView
import com.paymong.wear.ui.view.mainPager.MainPagerView
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainView () {
    val navController = rememberSwipeDismissableNavController()
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 1) { 4 }
    val scrollPage = fun(page: Int) { coroutineScope.launch { pagerState.animateScrollToPage(page) } }

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = NavItem.Login.route
    ) {
        composable(route = NavItem.Login.route) {
            LoginView(navController = navController)
        }
        composable(route = NavItem.MainPager.route) {
            MainPagerView(navController = navController, scrollPage = scrollPage, pagerState = pagerState)
        }
        composable(route = NavItem.SlotPick.route) {

        }
    }
}