package com.paymong.wear.ui.view.main.configure

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text

@Composable
fun ConfigureButton(
    icon: ImageVector,
    label: String,
    secondaryLabel: String,
    onClick: () -> Unit
) {
    Chip(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        onClick = onClick,
        enabled = true,
        label = {
            Text(
                text = label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis, fontSize = 10.sp,
                modifier = Modifier.padding(bottom = 3.dp)
            )
        },
        secondaryLabel = {
            Text(
                text = secondaryLabel,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 7.sp
            )
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(ChipDefaults.IconSize)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
        colors = ChipDefaults.chipColors(
            contentColor = Color.White,
            backgroundColor = Color.Black.copy(alpha = 0.4f)
        )
    )
}