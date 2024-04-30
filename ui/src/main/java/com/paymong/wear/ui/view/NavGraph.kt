package com.paymong.wear.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.wear.ui.global.resource.NavItem
import com.paymong.wear.ui.view.charge.menu.ChargeMenuView
import com.paymong.wear.ui.view.charge.payPoint.ChargePayPointView
import com.paymong.wear.ui.view.charge.starPoint.ChargeStarPointView
import com.paymong.wear.ui.view.collection.mapDetail.CollectionMapView
import com.paymong.wear.ui.view.collection.menu.CollectionMenuView
import com.paymong.wear.ui.view.collection.mongDetail.CollectionMongView
import com.paymong.wear.ui.view.collection.select.CollectionSelectView
import com.paymong.wear.ui.view.feed.menu.FeedMenuView
import com.paymong.wear.ui.view.feed.select.FeedSelectView
import com.paymong.wear.ui.view.feedback.FeedbackView
import com.paymong.wear.ui.view.initLanding.InitLandingView
import com.paymong.wear.ui.view.main.pager.PagerView
import com.paymong.wear.ui.view.main.pager.PAGE_LENGTH
import com.paymong.wear.ui.view.slotSelect.SlotSelectView
import com.paymong.wear.ui.view.training.select.TrainingSelectView
import com.paymong.wear.ui.view.training.jumping.TrainingJumpingView
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavGraph() {
    val navController = rememberSwipeDismissableNavController()
    /** MainView 페이지 상태 **/
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 1) { PAGE_LENGTH }
    val scrollPage = fun(page: Int) { coroutineScope.launch { pagerState.animateScrollToPage(page) } }

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = NavItem.InitLanding.route
    ) {
        /** 앱 시작 로딩 **/
        composable(route = NavItem.InitLanding.route) {
            InitLandingView(navController, scrollPage)
        }
        /** 메인 **/
        composable(route = NavItem.Main.route) {
            PagerView(navController, scrollPage, pagerState)
        }
        /** 슬롯 선택 **/
        composable(route = NavItem.SlotSelect.route) {
            SlotSelectView(navController, scrollPage)
        }
        /** 상점 **/
        navigation(startDestination = NavItem.FeedMenu.route, route = NavItem.FeedNested.route) {
            composable(route = NavItem.FeedMenu.route) {
                FeedMenuView(navController)
            }
            composable(
                route = "${NavItem.FeedSelect.route}/{code}",
                arguments = listOf(
                    navArgument("code") {
                        type = NavType.StringType
                    }
                )
            ) {
                val code = it.arguments!!.getString("code")
                FeedSelectView(navController, scrollPage, if(code.isNullOrEmpty()) "FOOD" else code )
            }
        }
        /** 컬렉션 **/
        navigation(startDestination = NavItem.CollectionMenu.route, route = NavItem.CollectionNested.route) {
            composable(route = NavItem.CollectionMenu.route) {
                CollectionMenuView(navController)
            }
            composable(
                route = "${NavItem.CollectionSelect.route}/{code}",
                arguments = listOf(
                    navArgument("code") {
                        type = NavType.StringType
                    }
                )
            ) {
                val code = it.arguments!!.getString("code")
                CollectionSelectView(navController, if(code.isNullOrEmpty()) "MONG" else code )
            }
            composable(
                route = "${NavItem.CollectionMong.route}/{code}",
                arguments = listOf(
                    navArgument("code") {
                        type = NavType.StringType
                    }
                )
            ) {
                val code = it.arguments!!.getString("code")
                CollectionMongView(navController, if(code.isNullOrEmpty()) "CH444" else code )
            }
            composable(
                route = "${NavItem.CollectionMap.route}/{code}",
                arguments = listOf(
                    navArgument("code") {
                        type = NavType.StringType
                    }
                )
            ) {
                val code = it.arguments!!.getString("code")
                CollectionMapView(navController, scrollPage, if(code.isNullOrEmpty()) "MP000" else code)
            }
        }
        /** 훈련 **/
        navigation(startDestination = NavItem.TrainingSelect.route, route = NavItem.TrainingNested.route) {
            composable(route = NavItem.TrainingSelect.route) {
                TrainingSelectView(navController)
            }
            composable(route = NavItem.TrainingJumping.route) {
                TrainingJumpingView(navController)
            }
        }
        /** 포인트 충전 **/
        navigation(startDestination = NavItem.ChargeMenu.route, route = NavItem.ChargeNested.route) {
            composable(route = NavItem.ChargeMenu.route) {
                ChargeMenuView(navController)
            }
            composable(route = NavItem.ChargeStarPoint.route) {
                ChargeStarPointView(navController)
            }
            composable(route = NavItem.ChargePayPoint.route) {
                ChargePayPointView(navController)
            }
        }
        /** 피드백 **/
        composable(route = NavItem.Feedback.route) {
            FeedbackView(navController)
        }
    }
}
