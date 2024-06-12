package com.mongs.wear.ui.global.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Text
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.component.background.CollectionNestedBackground
import com.mongs.wear.ui.global.component.button.BlueButton
import com.mongs.wear.ui.global.component.common.Mong
import com.mongs.wear.ui.global.resource.MapResourceCode
import com.mongs.wear.ui.global.resource.MongResourceCode
import com.mongs.wear.ui.global.theme.DAL_MU_RI
import com.mongs.wear.ui.global.theme.PaymongNavy
import com.mongs.wear.ui.global.theme.PaymongPurple
import com.mongs.wear.ui.global.theme.PaymongWhite


@Composable
fun MapCollectionDetailDialog(
    mapCode: String,
    setBackground: (String) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
    ) {
        val setBackgroundDialog = remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
        ) {
            Image(
                painter = painterResource(MapResourceCode.valueOf(mapCode).code),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp)
                .zIndex(2f)
        ) {
            BlueButton(
                text = "배경 설정",
                width = 100,
                onClick = { setBackgroundDialog.value = true }
            )
        }

        if (setBackgroundDialog.value) {
            ConfirmDialog(
                text = "배경화면으로\n설정하시겠습니까?",
                confirm = { setBackground(mapCode) },
                cancel = { setBackgroundDialog.value = false },
                modifier = Modifier.zIndex(3f)
            )
        }
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun MapCollectionDetailDialogPreview() {
    Box {
        MapCollectionDetailDialog(
            mapCode = "MP001",
            setBackground = {},
            onClick = {},
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun LargeMapCollectionDetailDialogPreview() {
    Box {
        MapCollectionDetailDialog(
            mapCode = "MP001",
            setBackground = {},
            onClick = {},
        )
    }
}