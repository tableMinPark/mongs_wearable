package com.paymong.wear.ui.view.main.condition.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.CircularProgressIndicator


@Composable
fun Condition(
    icon: Int,
    progress: Float,
    indicatorColor: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(6.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
        CircularProgressIndicator(
            modifier = Modifier.size(60.dp),
            startAngle = 271f,
            endAngle = 270f,
            progress = progress / 100,
            strokeWidth = 4.dp,
            indicatorColor = indicatorColor,
        )
    }
}