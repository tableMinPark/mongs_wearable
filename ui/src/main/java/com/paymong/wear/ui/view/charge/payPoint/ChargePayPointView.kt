package com.paymong.wear.ui.view.charge.payPoint

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.paymong.wear.ui.global.component.ChargePayPointBackground
import com.paymong.wear.ui.global.component.ChargeStarPointBackground

@Composable
fun ChargePayPointView(
    navController: NavController,
){
    Box {
        /** Background **/
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            ChargePayPointBackground()
        }

        ChargePayPointContent()
    }

    LaunchedEffect(Unit) {}
}