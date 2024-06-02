package com.paymong.wear.ui.view.mainSlot

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.paymong.wear.ui.R
import com.paymong.wear.ui.global.component.background.MainPagerBackground
import kotlinx.coroutines.delay

private val imageList = listOf(
    R.drawable.create_effect_1,
    R.drawable.create_effect_2,
    R.drawable.create_effect_3,
)
private val delayList = listOf(
    100L,
    300L,
    300L,
    300L
)

@Composable
fun EvolutionEffect(
    modifier: Modifier = Modifier.zIndex(0f),
) {
    var nowStep by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        for (step in 0..2) {
            nowStep = step
            delay(delayList[step])
        }
    }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier.fillMaxSize(),
    ) {
        Image(
            modifier = Modifier.fillMaxWidth().height(160.dp),
            painter = painterResource(imageList[nowStep]),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun EvolutionEffectPreview() {
    MainPagerBackground()
    EvolutionEffect()
}

