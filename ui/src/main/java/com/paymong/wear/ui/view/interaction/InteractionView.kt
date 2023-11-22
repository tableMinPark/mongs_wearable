package com.paymong.wear.ui.view.interaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.paymong.wear.ui.code.NavItem

@Composable
fun InteractionView(
    navController: NavController
) {
    Column {
        Text(text = "InteractionView")
        Row {
            Button(onClick = { navController.navigate(NavItem.Feed.route) }) {
                Text(text = "피딩")
            }
            Button(onClick = { navController.navigate(NavItem.Activity.route) }) {
                Text(text = "활동")
            }
            Button(onClick = { navController.navigate(NavItem.Battle.route) }) {
                Text(text = "배틀")
            }
        }
    }
}