package com.paymong.wear.ui.view

import androidx.compose.runtime.Composable
import androidx.navigation.navigation
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.paymong.wear.ui.code.NavItem
import com.paymong.wear.ui.view.interaction.activity.ActivityLandingView
import com.paymong.wear.ui.view.interaction.activity.training.TrainingView
import com.paymong.wear.ui.view.interaction.activity.walk.WalkView
import com.paymong.wear.ui.view.interaction.battle.BattleActiveView
import com.paymong.wear.ui.view.interaction.battle.BattleConnectView
import com.paymong.wear.ui.view.interaction.battle.BattleEndView
import com.paymong.wear.ui.view.interaction.battle.BattleFindView
import com.paymong.wear.ui.view.interaction.battle.BattleLandingView
import com.paymong.wear.ui.view.interaction.battle.BattleLoadingView
import com.paymong.wear.ui.view.interaction.battle.BattleSelectView
import com.paymong.wear.ui.view.interaction.battle.BattleSelectBeforeView
import com.paymong.wear.ui.view.interaction.feed.FeedLandingView
import com.paymong.wear.ui.view.interaction.feed.StoreView

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavGraph () {
    val pagerState = rememberPagerState(1)
    val navController = rememberSwipeDismissableNavController()

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = NavItem.MainLanding.route
    ) {
        // 로딩
        composable(route = NavItem.MainLanding.route) {
            MainLanding(navController)
        }
        // 메인
        composable(route = NavItem.Main.route) {
            Main(pagerState = pagerState, navController = navController)
        }
        // 활동
        navigation(startDestination = NavItem.ActivityLanding.route, route = NavItem.Activity.route) {
            composable(route = NavItem.ActivityLanding.route) {
                ActivityLandingView()
            }
            composable(route = NavItem.Training.route) {
                TrainingView()
            }
            composable(route = NavItem.Walk.route) {
                WalkView()
            }
        }
        // 피딩
        navigation(startDestination = NavItem.FeedLanding.route, route = NavItem.Feed.route) {
            composable(route = NavItem.FeedLanding.route) {
                FeedLandingView()
            }
            composable(route = NavItem.Store.route) {
                StoreView()
            }
        }
        // 배틀
        navigation(startDestination = NavItem.BattleLanding.route, route = NavItem.Battle.route) {
            composable(route = NavItem.BattleLanding.route) {
                BattleLandingView()
            }
            composable(route = NavItem.BattleConnect.route) {
                BattleConnectView()
            }
            composable(route = NavItem.BattleLoading.route) {
                BattleLoadingView()
            }
            composable(route = NavItem.BattleFind.route) {
                BattleFindView()
            }
            composable(route = NavItem.BattleActive.route) {
                BattleActiveView()
            }
            composable(route = NavItem.BattleSelectBefore.route) {
                BattleSelectBeforeView()
            }
            composable(route = NavItem.BattleSelect.route) {
                BattleSelectView()
            }
            composable(route = NavItem.BattleEnd.route) {
                BattleEndView()
            }
        }
    }
}