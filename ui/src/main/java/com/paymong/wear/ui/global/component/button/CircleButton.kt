package com.paymong.wear.ui.global.component.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.MaterialTheme
import com.paymong.wear.ui.R

@Composable
fun CircleButton(
    icon: Int,
    border: Int,
    size: Int = 54,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size.dp)
            .background(
                color = Color.Transparent,
                shape = MaterialTheme.shapes.large
            )
            .clickable (
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ) {
        Image(
            alpha = 0.6f,
            painter = painterResource(R.drawable.interaction_bnt),
            contentDescription = null,
            modifier = Modifier.zIndex(0f)
        )
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier
                .size((size / 2).dp)
                .zIndex(1f)
        )
        Image(
            painter = painterResource(border),
            contentDescription = null,
            modifier = Modifier.zIndex(1.2f)
        )
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun LongBlueButtonPreview() {
    CircleButton(
        icon = R.drawable.feed,
        border = R.drawable.interaction_bnt_yellow,
        onClick = {},
    )
}
