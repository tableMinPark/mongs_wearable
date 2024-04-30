package com.paymong.wear.ui.view.slotSelect.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text

@Composable
fun InputName(
    inputName: String,
    changeInputName: (String) -> Unit
) {
    BasicTextField(
        value = inputName,
        onValueChange = { changeInputName(it) },
        decorationBox = {
            Column(
                modifier = Modifier.drawWithContent {
                    drawContent()
                    drawLine(
                        color = Color(0xFFEFEFEF),
                        start = Offset(
                            x = 0f,
                            y = size.height - 1.dp.toPx(),
                        ),
                        end = Offset(
                            x = size.width,
                            y = size.height - 1.dp.toPx(),
                        ),
                        strokeWidth = 1.dp.toPx(),
                    )
                },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.width(130.dp)
                ) {
                    Text(text = inputName, fontSize = 15.sp, textAlign = TextAlign.Center)
                }
                Spacer( modifier = Modifier.height(15.dp) )
            }
        }
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(36.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    changeInputName("랜덤 이름")
                },
            )
    ) {
        Icon(
            modifier = Modifier.size(36.dp),
            imageVector = Icons.Default.Refresh,
            contentDescription = null,
            tint = Color(0xFFA8A8A8),
        )
    }
}