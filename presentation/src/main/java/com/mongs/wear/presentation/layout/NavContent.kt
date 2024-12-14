package com.mongs.wear.presentation.layout

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.navigation
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.mongs.wear.presentation.assets.NavItem
import com.mongs.wear.presentation.pages.battle.match.BattleMatchView
import com.mongs.wear.presentation.pages.battle.menu.BattleMenuView
import com.mongs.wear.presentation.pages.collection.map.CollectionMapPickView
import com.mongs.wear.presentation.pages.collection.menu.CollectionMenuView
import com.mongs.wear.presentation.pages.collection.mong.CollectionMongPickView
import com.mongs.wear.presentation.pages.feed.food.FeedFoodPickView
import com.mongs.wear.presentation.pages.feed.menu.FeedMenuView
import com.mongs.wear.presentation.pages.feed.snack.FeedSnackPickView
import com.mongs.wear.presentation.pages.feedback.FeedbackView
import com.mongs.wear.presentation.pages.help.menu.HelpMenuView
import com.mongs.wear.presentation.pages.login.LoginView
import com.mongs.wear.presentation.pages.main.layout.MainPagerView
import com.mongs.wear.presentation.pages.payment.chargeStartPoint.PaymentChargeStarPointView
import com.mongs.wear.presentation.pages.payment.exchangePayPoint.PaymentExchangePayPointView
import com.mongs.wear.presentation.pages.payment.menu.PaymentMenuView
import com.mongs.wear.presentation.pages.slot.SlotPickView
import com.mongs.wear.presentation.pages.training.jumping.TrainingJumpingView
import com.mongs.wear.presentation.pages.training.menu.TrainingMenuView
import kotlinx.coroutines.launch

@Composable
fun NavContent() {
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberSwipeDismissableNavController()
    val pagerState = rememberPagerState(initialPage = 2) { 5 }

    val pagerScroll = fun (page: Int) {
        coroutineScope.launch {
            pagerState.scrollToPage(page)
        }
    }

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
                pagerState = pagerState,
                pagerScroll = pagerScroll,
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
                FeedFoodPickView(navController = navController, scrollPage = pagerScroll)
            }
            composable(route = NavItem.FeedSnackPick.route) {
                FeedSnackPickView(navController = navController, scrollPage = pagerScroll)
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
            SlotPickView(navController = navController, scrollPage = pagerScroll)
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
            FeedbackView()
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

        navigation(
            startDestination = NavItem.HelpMenu.route,
            route = NavItem.HelpNested.route
        ) {
            composable(route = NavItem.HelpMenu.route) {
                HelpMenuView()
            }
        }
    }
}