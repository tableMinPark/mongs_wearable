package com.paymong.wear.ui.view.main

import androidx.compose.runtime.Composable
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.paymong.wear.ui.global.resource.NavItem
import com.paymong.wear.ui.view.login.LoginView
import com.paymong.wear.ui.view.mainPager.MainPagerView


@Composable
fun MainView () {
    val navController = rememberSwipeDismissableNavController()

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = NavItem.Login.route
    ) {
        composable(route = NavItem.Login.route) {
            LoginView(navController)
        }
        composable(route = NavItem.MainPager.route) {
            MainPagerView(navController)
        }
    }
}