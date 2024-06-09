package com.mongs.wear.ui.global.component.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.resource.MongResourceCode
import com.mongs.wear.domain.code.StateCode

@Composable
fun Mong(
    mong: MongResourceCode,
    state: StateCode = StateCode.NORMAL,
    isHappy: Boolean = false,
    isEating: Boolean = false,
    isSleeping: Boolean = false,
    onClick: () -> Unit = {},
    ratio: Float = 1f,
    isPng: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components { add(ImageDecoderDecoder.Factory()) }
        .build()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
    ) {
        Box(
            modifier = Modifier.zIndex(0f)
        ) {
            if (isPng) {
                Image(
                    painter = painterResource(id = mong.pngCode),
                    contentDescription = null,
                    modifier = Modifier.size((120 * ratio).dp)
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = mong.gifCode,
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier.size((120 * ratio).dp)
                )
            }
        }

        if (!isPng && mong.hasExpression) {
            Box(
                modifier = Modifier.zIndex(1f)
            ) {
                val expression = when (state) {
                    StateCode.SICK -> R.drawable.sad
                    StateCode.TIRED -> R.drawable.depressed
                    StateCode.HUNGRY -> R.drawable.sulky
                    else -> {
                        if (isHappy) {
                            R.drawable.happy
                        } else if (isEating) {
                            R.drawable.eating
                        } else if (isSleeping) {
                            R.drawable.sleeping
                        } else {
                            R.drawable.smile
                        }
                    }
                }

                Image(
                    painter = rememberAsyncImagePainter(model = expression, imageLoader = imageLoader),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size((35 * ratio).dp)
                        .offset(y = mong.yOffset.dp, x = mong.xOffset.dp)
                )
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun MongPreview() {
    Mong(mong = MongResourceCode.CH201, isPng = false)
}