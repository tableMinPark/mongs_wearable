package com.mongs.wear.ui.view.mainSlot.effect

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.component.background.MainPagerBackground
import kotlinx.coroutines.delay

private val imageList = listOf(
    R.drawable.star_1,
    R.drawable.star_2,
    R.drawable.star_3,
    R.drawable.graduation,
)
private val delayList = listOf(
    300L,
    400L,
    500L,
    2000L,
)

@Composable
fun GraduationEffect(
    graduationReady: () -> Unit = {},
    modifier: Modifier = Modifier.zIndex(0f),
) {
    var nowStep by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        for (step in 0..3) {
            nowStep = step
            delay(delayList[step])
        }
        graduationReady()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Image(
            modifier = Modifier.size(145.dp),
            painter = painterResource(imageList[nowStep]),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun GraduationEffectPreview() {
    Box {
        MainPagerBackground()
        GraduationEffect()
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun GraduationEffectLargePreview() {
    Box {
        MainPagerBackground()
        GraduationEffect()
    }
}