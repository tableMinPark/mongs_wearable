package com.paymong.wear.ui.view.main.slotAction

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon

@Composable
fun SlotActionBackButton(
    color: Color,
    size: Int = 38,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(size.dp)
    ) {
        Button(
            modifier = Modifier.border(color = color, width = 2.dp, shape = CircleShape),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White.copy(alpha = 0.8f)),
            onClick = onClick
        ) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null, tint = color)
        }
    }
}

@Composable
fun SlotActionButton(
    icon: Int,
    border: Color,
    size: Int = 48,     // 최소 클릭 범위 (48dp x 48dp)
    xOffset: Int,
    yOffset: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .offset(xOffset.dp, yOffset.dp)
    ) {
        Button(
            modifier = Modifier.border(color = border, width = 2.dp, shape = CircleShape),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White.copy(alpha = 0.8f)),
            onClick = onClick
        ) {
            Image(
                modifier = Modifier.padding(10.dp),
                painter = painterResource(icon),
                contentDescription = null
            )
        }
    }
}