package com.paymong.wear.ui.view.charge.starPoint

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.paymong.wear.ui.global.component.ChargeStarPointBackground

@Composable
fun ChargeStarPointView(
    navController: NavController,
){
    Box {
        /** Background **/
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            ChargeStarPointBackground()
        }

        ChargeStarPointContent()
    }

    LaunchedEffect(Unit) {}
}