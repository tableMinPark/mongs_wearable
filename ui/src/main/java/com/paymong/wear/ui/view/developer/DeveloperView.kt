package com.paymong.wear.ui.view.developer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.Text


@Composable
fun DeveloperView(

) {

    /** Content **/
    DeveloperContent()
}

@Composable
fun DeveloperContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "개발자 페이지")
    }
}