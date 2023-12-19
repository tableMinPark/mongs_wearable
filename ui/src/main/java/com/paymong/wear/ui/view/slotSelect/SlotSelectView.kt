package com.paymong.wear.ui.view.slotSelect

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.wear.compose.material.Text

@Composable
fun SlotSelectView(
    navController: NavController
) {

    /** Content **/
    SlotSelectContent()
}

@Composable
fun SlotSelectContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "슬롯 선택 페이지")
    }
}