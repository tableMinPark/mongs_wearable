package com.paymong.wear.ui.view_.common

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.paymong.wear.ui.code.MapCode

@Composable
fun InitLandingBackground() {
    val mapCode = MapCode.MP000.code

    Image(
        painter = painterResource(mapCode),
        contentDescription = "InitLandingBackground",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun MainBackground() {
    val mapCode = MapCode.MP000.code

    Image(
        painter = painterResource(mapCode),
        contentDescription = "MainBackground",
        contentScale = ContentScale.Crop
    )
}