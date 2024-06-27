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
import com.mongs.wear.ui.view.battleMatch.BattleMatchView
import com.mongs.wear.ui.view.battleMenu.BattleMenuView
import com.mongs.wear.ui.view.collectionMapPick.CollectionMapPickView
import com.mongs.wear.ui.view.collectionMenu.CollectionMenuView
import com.mongs.wear.ui.view.collectionMongPick.CollectionMongPickView
import com.mongs.wear.ui.view.feedFoodPick.FeedFoodPickView
import com.mongs.wear.ui.view.feedMenu.FeedMenuView
import com.mongs.wear.ui.view.feedSnackPick.FeedSnackPickView
import com.mongs.wear.ui.view.feedback.FeedbackView
import com.mongs.wear.ui.view.login.LoginView
import com.mongs.wear.ui.view.mainPager.MainPagerView
import com.mongs.wear.ui.view.paymentChargeStarPoint.PaymentChargeStarPointView
import com.mongs.wear.ui.view.paymentExchangePayPoint.PaymentExchangePayPointView
import com.mongs.wear.ui.view.paymentMenu.PaymentMenuView
import com.mongs.wear.ui.view.slotPick.SlotPickView
import com.mongs.wear.ui.view.trainingJumping.TrainingJumpingView
import com.mongs.wear.ui.view.trainingMenu.TrainingMenuView
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavContent() {
    val navController = rememberSwipeDismissableNavController()
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 2) { 5 }
    val scrollPage = fun(page: Int) { coroutineScope.launch { pagerState.scrollToPage(page) } }

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = NavItem.Login.route
    ) {
        composable(route = NavItem.Login.route) {
            LoginView(navController = navController)
        }

        composable(route = NavItem.MainPager.route) {
            MainPagerView(
                navController = navController,
                scrollPage = scrollPage,
                pagerState = pagerState
            )
        }

        navigation(
            startDestination = NavItem.FeedMenu.route,
            route = NavItem.FeedNested.route
        ) {
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

        navigation(
            startDestination = NavItem.CollectionMenu.route,
            route = NavItem.CollectionNested.route
        ) {
            composable(route = NavItem.CollectionMenu.route) {
                CollectionMenuView(navController = navController)
            }
            composable(route = NavItem.CollectionMapPick.route) {
                CollectionMapPickView(navController = navController)
            }
            composable(route = NavItem.CollectionMongPick.route) {
                CollectionMongPickView(navController = navController)
            }
        }

        composable(route = NavItem.SlotPick.route) {
            SlotPickView(navController = navController, scrollPage = scrollPage)
        }

        navigation(
            startDestination = NavItem.PaymentMenu.route,
            route = NavItem.PaymentNested.route
        ) {
            composable(route = NavItem.PaymentMenu.route) {
                PaymentMenuView(navController = navController)
            }
            composable(route = NavItem.PaymentChargeStarPoint.route) {
                PaymentChargeStarPointView(navController = navController)
            }
            composable(route = NavItem.PaymentExchangePayPoint.route) {
                PaymentExchangePayPointView(navController = navController)
            }
        }

        composable(route = NavItem.Feedback.route) {
            FeedbackView(navController = navController)
        }

        navigation(
            startDestination = NavItem.TrainingMenu.route,
            route = NavItem.TrainingNested.route
        ) {
            composable(route = NavItem.TrainingMenu.route) {
                TrainingMenuView(navController = navController)
            }
            composable(route = NavItem.TrainingJumping.route) {
                TrainingJumpingView(navController = navController)
            }
        }

        navigation(
            startDestination = NavItem.BattleMenu.route,
            route = NavItem.BattleNested.route
        ) {
            composable(route = NavItem.BattleMenu.route) {
                BattleMenuView(navController = navController)
            }
            composable(route = NavItem.BattleMatch.route) {
                BattleMatchView(navController = navController)
            }
        }
    }
}