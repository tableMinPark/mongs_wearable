package com.mongs.wear.ui.view.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.navigation
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.mongs.wear.ui.global.resource.NavItem
import com.mongs.wear.ui.view.collectionMenu.CollectionMenuView
import com.mongs.wear.ui.view.feedFoodPick.FeedFoodPickView
import com.mongs.wear.ui.view.feedMenu.FeedMenuView
import com.mongs.wear.ui.view.feedSnackPick.FeedSnackPickView
import com.mongs.wear.ui.view.login.LoginView
import com.mongs.wear.ui.view.mainPager.MainPagerView
import com.mongs.wear.ui.view.slotPick.SlotPickView
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

        navigation(startDestination = NavItem.FeedMenu.route, route = NavItem.FeedNested.route) {
            composable(route = NavItem.FeedMenu.route) {
                FeedMenuView(navController = navController)
            }
            composable(route = NavItem.FeedFoodPick.route) {
                FeedFoodPickView(navController = navController, scrollPage = scrollPage)
            }
            composable(route = NavItem.FeedSnackPick.route) {
                FeedSnackPickView(navController = navController, scrollPage = scrollPage)
            }
        }

        navigation(startDestination = NavItem.CollectionMenu.route, route = NavItem.CollectionNested.route) {
            composable(route = NavItem.CollectionMenu.route) {
                CollectionMenuView(navController = navController)
            }
            composable(route = NavItem.CollectionMapPick.route) {

            }
            composable(route = NavItem.CollectionMapDetail.route) {

            }
            composable(route = NavItem.CollectionMongPick.route) {

            }
            composable(route = NavItem.CollectionMongDetail.route) {

            }
        }

        composable(route = NavItem.SlotPick.route) {
            SlotPickView(navController = navController)
        }

        navigation(startDestination = NavItem.PaymentMenu.route, route = NavItem.PaymentNested.route) {
            composable(route = NavItem.PaymentMenu.route) {

            }
            composable(route = NavItem.PaymentChargeStarPoint.route) {

            }
            composable(route = NavItem.PaymentExchangePayPoint.route) {

            }
        }

        composable(route = NavItem.Feedback.route) {

        }
    }
}