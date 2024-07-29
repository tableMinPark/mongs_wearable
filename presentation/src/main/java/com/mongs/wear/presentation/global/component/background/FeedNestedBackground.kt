package com.mongs.wear.presentation.global.component.background

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.mongs.wear.presentation.global.resource.MapResourceCode

@Composable
fun FeedNestedBackground(
    modifier: Modifier = Modifier.zIndex(0f)
) {
    val mapResourceCode = MapResourceCode.MP000.code

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(mapResourceCode),
            contentDescription = "FeedNestedBackground",
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun FeedNestedBackgroundPreview() {
    FeedNestedBackground()
}