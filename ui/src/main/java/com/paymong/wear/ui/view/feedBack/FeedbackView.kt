package com.paymong.wear.ui.view.feedBack

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.wear.compose.material.Text


@Composable
fun FeedbackView(
    navController: NavController
) {

    /** Content **/
    FeedbackContent()
}

@Composable
fun FeedbackContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "피드백 페이지")
    }
}