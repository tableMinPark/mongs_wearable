package com.paymong.wear.ui.view.walk

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.wear.compose.material.Text

@Composable
fun WalkView(
    navController: NavController
) {

    /** Content **/
    WalkContent()
}

@Composable
fun WalkContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "산책 페이지")
    }
}