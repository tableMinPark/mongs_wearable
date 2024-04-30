package com.paymong.wear.ui.view.training.select

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.paymong.wear.ui.global.component.TrainingSelectBackground
import com.paymong.wear.ui.global.resource.NavItem

@Composable
fun TrainingSelectView(
    navController: NavController
) {
    Box {
        /** Background **/
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            TrainingSelectBackground()
        }

        TrainingSelectContent(
            trainingBasketball = { navController.navigate(NavItem.TrainingJumping.route) }
        )
    }
}