package com.mongs.wear.presentation.global.dialog.collection

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mongs.wear.presentation.global.component.button.BlueButton
import com.mongs.wear.presentation.global.dialog.common.ConfirmDialog
import com.mongs.wear.presentation.global.resource.MapResourceCode


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