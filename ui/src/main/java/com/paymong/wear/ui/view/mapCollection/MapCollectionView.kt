package com.paymong.wear.ui.view.mapCollection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.wear.compose.material.Text

@Composable
fun MapCollectionView(
    navController: NavController
) {

    /** Content **/
    MapCollectionContent()
}

@Composable
fun MapCollectionContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "맵 컬렉션 페이지")
    }
}