package com.paymong.wear.ui.view.main.slot.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymong.wear.ui.R

@Composable
fun Poop(
    poopCount: Int,
    modifier: Modifier,
) {
    val poopPadding = arrayOf(
        PaddingValues(end = 80.dp,bottom = 10.dp),
        PaddingValues(start = 76.dp,bottom = 12.dp),
        PaddingValues(end = 60.dp,bottom = 2.dp),
        PaddingValues(start = 54.dp)
    )
    val poop = painterResource(R.drawable.poops)
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
    ) {
        for (count in 1..poopCount) {
            val idx = count - 1
            Image(
                painter = poop,
                modifier = Modifier
                    .padding(poopPadding[idx])
                    .size(25.dp),
                contentDescription = null
            )
        }
    }
}