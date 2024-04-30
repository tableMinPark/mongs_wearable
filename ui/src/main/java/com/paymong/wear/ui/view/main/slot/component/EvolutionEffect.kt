package com.paymong.wear.ui.view.main.slot.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.paymong.wear.ui.R
import kotlinx.coroutines.delay

@Composable
fun EvolutionEffect(
    modifier: Modifier,
) {
    var effectEnd by remember { mutableStateOf(false) }
    val delayList = listOf(100L, 300L, 300L, 300L)
    var nowStep by remember { mutableIntStateOf(0) }
    val imageList = listOf(
        painterResource(R.drawable.create_effect_1),
        painterResource(R.drawable.create_effect_2),
        painterResource(R.drawable.create_effect_3)
    )

    LaunchedEffect(Unit) {
        for (step in 0..2) {
            nowStep = step
            delay(delayList[step])
        }
        effectEnd = true
    }

    if (!effectEnd) {
        Box(
            modifier = modifier,
        ) {
            Image(
                painter = imageList[nowStep],
                contentDescription = null,
            )
        }
    }
}