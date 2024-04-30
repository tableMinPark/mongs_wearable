package com.paymong.wear.ui.view.training.jumping

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.paymong.wear.ui.global.component.TrainingJumpingBackground

@Composable
fun TrainingJumpingView(
    navController: NavController
) {
    Box {
        /** Background **/
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            TrainingJumpingBackground()
        }

        TrainingJumpingContent()
    }
}