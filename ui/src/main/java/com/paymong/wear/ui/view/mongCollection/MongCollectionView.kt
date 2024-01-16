package com.paymong.wear.ui.view.mongCollection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.Text

@Composable
fun MongCollectionView(

) {

    /** Content **/
    MongCollectionContent()
}

@Composable
fun MongCollectionContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "몽 컬랙션 페이지")
    }
}