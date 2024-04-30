package com.paymong.wear.ui.view.main.interaction.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.MaterialTheme
import com.paymong.wear.ui.R


@Composable
fun Interaction(
    icon: Int,
    border: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box (
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(54.dp)
            .background(
                color = Color.Transparent,
                shape = MaterialTheme.shapes.large
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ) {
        Image(
            alpha = 0.6f,
            painter = painterResource(R.drawable.interaction_bnt),
            contentDescription = null,
            modifier = Modifier.zIndex(1.1f)
        )
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier
                .size(28.dp)
                .zIndex(1.2f)
        )
        Image(
            painter = painterResource(border),
            contentDescription = null,
            modifier = Modifier.zIndex(1.2f)
        )
    }
}