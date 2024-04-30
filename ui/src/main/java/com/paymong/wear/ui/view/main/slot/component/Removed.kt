package com.paymong.wear.ui.view.main.slot.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.R

@Composable
fun Removed(
    isPageChange: Boolean,
    mongModifier: Modifier,
    textModifier: Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = mongModifier
    ) {
        Image(
            modifier = Modifier.size(120.dp),
            painter = painterResource(R.drawable.rip),
            contentDescription = null
        )
    }
    if (!isPageChange) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = textModifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.6f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                )
        ) {
            Text(
                text = "몽이 삭제 되었습니다\n슬롯을 변경해 주세요",
            )
        }
    }
}
