package com.paymong.wear.ui.view.charge.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.paymong.wear.ui.global.component.ChargeMenuBackground
import com.paymong.wear.ui.global.resource.NavItem
import com.paymong.wear.ui.global.component.FeedMenuBackground

@Composable
fun ChargeMenuView(
    navController: NavController
) {
    Box {
        /** Background **/
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            ChargeMenuBackground()
        }

        ChargeMenuContent(
            starPoint = { navController.navigate(NavItem.ChargeStarPoint.route) },
            payPoint = { navController.navigate(NavItem.ChargePayPoint.route) }
        )
    }
}
