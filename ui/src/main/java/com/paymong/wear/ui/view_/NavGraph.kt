package com.paymong.wear.ui.view_

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.wear.ui.code.NavItem
import com.paymong.wear.ui.view_.initLanding.InitLandingView
import com.paymong.wear.ui.view_.main.MainView

@Composable
fun NavGraph() {
    val navController = rememberSwipeDismissableNavController()

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = NavItem.InitLanding.route
    ) {
        /** 앱 시작 로딩 **/
        composable(route = NavItem.InitLanding.route) {
            InitLandingView(navController)
        }
        /** 메인 **/
        composable(route = NavItem.Main.route) {
            MainView(navController)
        }
        /** 슬롯 선택 **/
        composable(route = NavItem.SlotSelect.route) {
            TODO("슬롯 선택 화면")
        }
        /** 상점 **/
        navigation(startDestination = NavItem.FeedMenu.route, route = NavItem.FeedNested.route) {
            composable(route = NavItem.FeedMenu.route) {
                TODO("상점 메뉴 화면")
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
                TODO("상점 아이템 선택 화면")
                // FeedSelectView(navController, if(code.isNullOrEmpty()) "FD" else code )
            }
        }
        /** 훈련 **/
        navigation(startDestination = NavItem.TrainingLanding.route, route = NavItem.TrainingNested.route) {
            composable(route = NavItem.TrainingLanding.route) {
                TODO("훈련 시작 화면")
            }
            composable(route = NavItem.Training.route) {
                TODO("훈련 화면")
            }
        }
        /** 배틀 **/
        navigation(startDestination = NavItem.BattleLanding.route, route = NavItem.BattleNested.route) {
            composable(route = NavItem.BattleLanding.route) {
                TODO("배틀 시작 화면")
            }
            composable(route = NavItem.Battle.route) {
                TODO("배틀 화면")
            }
        }
        /** 컬렉션 **/
        navigation(startDestination = NavItem.CollectionMenu.route, route = NavItem.CollectionNested.route) {
            composable(route = NavItem.CollectionMenu.route) {
                TODO("컬렉션 메뉴 화면")
            }
            composable(route = NavItem.CollectionSelect.route) {
                TODO("컬렉션 아이템 선택 화면")
            }
            composable(route = NavItem.CollectionMong.route) {
                TODO("몽 컬렉션 상세 화면")
            }
            composable(route = NavItem.CollectionMap.route) {
                TODO("맵 컬렉션 상세 화면")
            }
        }
        /** 맵 수집 **/
        composable(route = NavItem.CollectMap.route) {
            TODO("맵 수집 화면")
        }
        /** 설정 **/
        composable(route = NavItem.Setting.route) {
            TODO("설정 화면")
        }
        /** 개발자 페이지 **/
        composable(route = NavItem.Developer.route) {
            TODO("개발자 화면")
        }
        /** 피드백 **/
        composable(route = NavItem.Feedback.route) {
            TODO("피드백 화면")
        }
    }
}

