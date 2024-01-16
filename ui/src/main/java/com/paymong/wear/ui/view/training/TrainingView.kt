package com.paymong.wear.ui.view.training

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.wear.compose.material.Text

@Composable
fun TrainingView(
    navController: NavController
) {

    /** Content **/
    TrainingContent()
}

@Composable
fun TrainingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "훈련 페이지")
    }
}