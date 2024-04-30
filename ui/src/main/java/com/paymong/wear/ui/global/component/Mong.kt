package com.paymong.wear.ui.global.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import com.paymong.wear.ui.R
import com.paymong.wear.ui.global.resource.MongResourceCode
import com.paymong.wear.domain.code.StateCode

@Composable
fun Mong(
    state: StateCode = StateCode.NORMAL,
    isHappy: Boolean = false,
    isEating: Boolean = false,
    isSleeping: Boolean = false,
    mong: MongResourceCode,
    onClick: () -> Unit = {},
    ratio: Float = 1f,
    isPng: Boolean = false,
    modifier: Modifier
) {
    /** gif 이미지 로더 **/
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components { add(ImageDecoderDecoder.Factory()) }
        .build()

    /** 몽 **/
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
    ) {
        /** 몸 **/
        Box(
            modifier = Modifier.zIndex(0f)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = if (isPng) mong.pngCode else mong.gifCode,
                    imageLoader = imageLoader
                ),
                contentDescription = null,
                modifier = Modifier
                    .size((120 * ratio).dp)
            )
        }
        /** 표정 **/
        val isEgg = mong.code.replace("CH", "").toInt() < 100
        if (!isPng && !isEgg) {
            Box(
                modifier = Modifier.zIndex(0f)
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
                    painter = rememberAsyncImagePainter(
                        model = expression,
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size((35 * ratio).dp)
                )
            }
        }
    }
}