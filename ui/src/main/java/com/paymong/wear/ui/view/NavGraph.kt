package com.paymong.wear.ui.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.wear.ui.code.NavItem
import com.paymong.wear.ui.view.battle.BattleView
import com.paymong.wear.ui.view.collection.CollectionView
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
        navigation(startDestination = NavItem.Feed.route, route = NavItem.FeedNested.route) {
            composable(route = NavItem.Feed.route) {
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
        // 뱌툴
        navigation(startDestination = NavItem.Battle.route, route = NavItem.BattleNested.route) {
            composable(route = NavItem.Battle.route) {
                BattleView(navController)
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
        // 컬랙션
        navigation(startDestination = NavItem.Collection.route, route = NavItem.CollectionNested.route) {
            composable(route = NavItem.Collection.route) {
                CollectionView()
            }
        }


//        // 활동
//        navigation(startDestination = NavItem.ActivityLanding.route, route = NavItem.Activity.route) {
//            composable(route = NavItem.ActivityLanding.route) {
//                ActivityLandingView()
//            }
//            composable(route = NavItem.Training.route) {
//                TrainingView()
//            }
//            composable(route = NavItem.Walk.route) {
//                WalkView()
//            }
//        }
//        // 피딩
//        navigation(startDestination = NavItem.FeedLanding.route, route = NavItem.Feed.route) {
//            composable(route = NavItem.FeedLanding.route) {
//                FeedLandingView()
//            }
//            composable(route = NavItem.Store.route) {
//                StoreView()
//            }
//        }
//        // 배틀
//        navigation(startDestination = NavItem.BattleLanding.route, route = NavItem.Battle.route) {
//            composable(route = NavItem.BattleLanding.route) {
//                BattleLandingView()
//            }
//            composable(route = NavItem.BattleConnect.route) {
//                BattleConnectView()
//            }
//            composable(route = NavItem.BattleLoading.route) {
//                BattleLoadingView()
//            }
//            composable(route = NavItem.BattleFind.route) {
//                BattleFindView()
//            }
//            composable(route = NavItem.BattleActive.route) {
//                BattleActiveView()
//            }
//            composable(route = NavItem.BattleSelectBefore.route) {
//                BattleSelectBeforeView()
//            }
//            composable(route = NavItem.BattleSelect.route) {
//                BattleSelectView()
//            }
//            composable(route = NavItem.BattleEnd.route) {
//                BattleEndView()
//            }
//        }
//        // 몽 컬렉션
//        composable(route = NavItem.Collection.route) {
//            CollectionView()
//        }
//        // 개발자 페이지
//        composable(route = NavItem.Developer.route) {
//            DeveloperView()
//        }
//        // 피드백
//        composable(route = NavItem.Feedback.route) {
//            FeedbackView(navController)
//        }
//        // 설정
//        composable(route = NavItem.Setting.route) {
//            SettingView(navController, pagerState, coroutineScope)
//        }
    }
}