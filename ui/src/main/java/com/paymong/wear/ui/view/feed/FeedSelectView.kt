package com.paymong.wear.ui.view.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.wear.compose.material.Text

@Composable
fun FeedSelectView(
    navController: NavController
) {

    /** Content **/
    FeedSelectContent()
}

@Composable
fun FeedSelectContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "상점 물건 선택 페이지")
    }
}