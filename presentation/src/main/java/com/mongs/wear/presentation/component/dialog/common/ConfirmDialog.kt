package com.mongs.wear.presentation.component.dialog.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.mongs.wear.presentation.assets.DAL_MU_RI
import com.mongs.wear.presentation.assets.MongsWhite
import com.mongs.wear.presentation.component.button.BlueButton

@Composable
fun ConfirmDialog(
    text: String = "",
    confirm: () -> Unit = {},
    cancel: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(color = Color.Black.copy(alpha = 0.95f))
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = cancel,
            )
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
            ) {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    fontFamily = DAL_MU_RI,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    color = MongsWhite,
                    maxLines = 3,
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
            ) {
                BlueButton(
                    text = "닫기",
                    onClick = cancel,
                )

                Spacer(modifier = Modifier.width(10.dp))

                BlueButton(
                    text = "확인",
                    onClick = confirm,
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun ConfirmDialogPreview() {
    ConfirmDialog(text = "삭제하시겠습니까?")
}