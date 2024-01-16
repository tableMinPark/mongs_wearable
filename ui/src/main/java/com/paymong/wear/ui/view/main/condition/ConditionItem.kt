package com.paymong.wear.ui.view.main.condition

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.CircularProgressIndicator

@Composable
fun ConditionButton(
    resourceId: Int,
    progress: State<Float>,
    indicatorColor: Color
) {
    Box(
        modifier = Modifier.padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(resourceId),
            contentDescription = null,
            modifier = Modifier.size(imageSize.dp)
        )
        CircularProgressIndicator(
            modifier = Modifier.size(circularSize.dp),
            startAngle = 271f,
            endAngle = 270f,
            progress = progress.value,
            strokeWidth = strokeWidth.dp,
            indicatorColor = indicatorColor,
        )
    }
}