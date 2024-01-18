package com.paymong.wear.ui.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.wear.ui.code.NavItem
import com.paymong.wear.ui.view.mapCollection.MapCollectionView
import com.paymong.wear.ui.view.mongCollection.MongCollectionView
import com.paymong.wear.ui.view.developer.DeveloperView
import com.paymong.wear.ui.view.feed.FeedSelectView
import com.paymong.wear.ui.view.feed.FeedView
import com.paymong.wear.ui.view.feedBack.FeedbackView
import com.paymong.wear.ui.view.initLanding.MainLandingView
import com.paymong.wear.ui.view.main.MainView
import com.paymong.wear.ui.view.setting.SettingView
import com.paymong.wear.ui.view.slotSelect.SlotSelectView
import com.paymong.wear.ui.view.training.TrainingView
import com.paymong.wear.ui.view.walk.WalkView


@Composable
fun NavGraph () {
    val navController = rememberSwipeDismissableNavController()

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = NavItem.InitLanding.route
    ) {
        // 앱 시작 로딩
        composable(route = NavItem.InitLanding.route) {
            MainLandingView(navController)
        }
        // 설정
        composable(route = NavItem.Setting.route) {
            SettingView()
        }
        // 개발자 페이지
        composable(route = NavItem.Developer.route) {
            DeveloperView()
        }
        // 피드백
        composable(route = NavItem.Feedback.route) {
            FeedbackView(navController)
        }
        // 메인
        navigation(startDestination = NavItem.Main.route, route = NavItem.MainNested.route) {
            // 메인 뷰 페이저
            composable(route = NavItem.Main.route) {
                MainView(navController)
            }
            // 슬롯 선택 페이지
            composable(route = NavItem.SlotSelect.route) {
                SlotSelectView(navController)
            }
        }
        // 상점
        navigation(startDestination = NavItem.FeedMenu.route, route = NavItem.FeedNested.route) {
            composable(route = NavItem.FeedMenu.route) {
                FeedView(navController)
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
                FeedSelectView(navController, if(code.isNullOrEmpty()) "FD" else code )
            }
        }
        // 훈련
        navigation(startDestination = NavItem.Training.route, route = NavItem.TrainingNested.route) {
            composable(route = NavItem.Training.route) {
                TrainingView(navController)
            }
        }
        // 산책
        navigation(startDestination = NavItem.Walk.route, route = NavItem.WalkNested.route) {
            composable(route = NavItem.Walk.route) {
                WalkView(navController)
            }
        }
        // 맵 컬랙션
        navigation(startDestination = NavItem.MapCollection.route, route = NavItem.MapCollectionNested.route) {
            composable(route = NavItem.MapCollection.route) {
                MapCollectionView(navController)
            }
        }
        // 몽 컬랙션
        navigation(startDestination = NavItem.MongCollection.route, route = NavItem.MongCollectionNested.route) {
            composable(route = NavItem.MongCollection.route) {
                MongCollectionView()
            }
        }
    }
}