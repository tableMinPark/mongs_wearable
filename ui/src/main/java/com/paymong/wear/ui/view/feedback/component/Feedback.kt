package com.paymong.wear.ui.view.feedback.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.R


@Composable
fun Feedback(
//    icon: Int,
//    border: Int,
    fontColor: Color = Color.White,
    backgroundColor: Color = Color.Gray,
    label: String,
    onClick: () -> Unit,
) {
    Chip(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        icon = {
//                Box(
//                    contentAlignment = Alignment.Center
//                ) {
//                    Image(
//                        alpha = 0.8f,
//                        painter = painterResource(R.drawable.interaction_bnt),
//                        contentDescription = null,
//                        modifier = Modifier.zIndex(0f)
//                    )
//                    Image(
//                        painter = painterResource(icon),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(24.dp)
//                            .zIndex(1f)
//                    )
//                    Image(
//                        painter = painterResource(border),
//                        contentDescription = null,
//                        modifier = Modifier.zIndex(1f)
//                    )
//                }
        },
        colors = ChipDefaults.chipColors(
            contentColor = fontColor,
            backgroundColor = backgroundColor.copy(alpha = 0.3f)
        ),
        label = {
            Text(
                text = label,
                maxLines = 1,
                fontSize = 13.sp,
                modifier = Modifier.padding(start = 14.dp)
            )
        }
    )
}