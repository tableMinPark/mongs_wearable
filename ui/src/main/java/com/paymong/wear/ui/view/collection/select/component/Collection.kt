package com.paymong.wear.ui.view.collection.select.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.wear.compose.material.Text


/** 컬렉션 아이템 **/
@Composable
fun Collection(
    disable: Boolean,
    border: Int,
    backgroundColor: Color,
    backgroundAlpha: Float,
    showDetail: () -> Unit,
    cantShowDetail: () -> Unit,
    modifier: Modifier,
    icon: @Composable () -> Unit
) {
    Box (
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .background(backgroundColor.copy(alpha = backgroundAlpha), shape = CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = if (!disable) showDetail else cantShowDetail
            )
    ) {
        /** 아이콘 **/
        if (disable) {
            Text(text = "?")
        } else {
            icon()
        }

        /** 테두리 **/
        Image(
            painter = painterResource(id = border),
            contentDescription = null,
        )
    }
}
