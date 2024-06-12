package com.mongs.wear.ui.global.component.background

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.mongs.wear.ui.global.resource.MapResourceCode

@Composable
fun MainPagerBackground(
    mapCode: String = MapResourceCode.MP000.name,
    backgroundAlpha: Float = 0f,
    modifier: Modifier = Modifier.zIndex(0f)
) {
    val mapResourceCode = MapResourceCode.valueOf(mapCode).code

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.Black.copy(alpha = backgroundAlpha))
                .fillMaxSize()
                .zIndex(1f)
        ) {
            Image(
                painter = painterResource(mapResourceCode),
                contentDescription = "MainPagerBackground",
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .background(color = Color.Black.copy(alpha = backgroundAlpha))
                .fillMaxSize()
                .zIndex(2f)
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun MainPagerBackgroundPreview() {
    MainPagerBackground(backgroundAlpha = 0.5f)
}