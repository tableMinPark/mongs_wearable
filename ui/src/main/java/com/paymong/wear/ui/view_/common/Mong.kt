package com.paymong.wear.ui.view_.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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
import com.paymong.wear.ui.code.MongCode
import com.paymong.wear.ui.code.StateCode
import com.paymong.wear.ui.view.common.character.characterBodySize
import com.paymong.wear.ui.view.common.character.characterFaceSize

@Composable
fun Mong(
    stateCode: State<String>,
    mongCode: State<String>,
    onClick: () -> Unit,
    ratio: Float = 1f
) {
    /** gif 이미지 로더 **/
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components { add(ImageDecoderDecoder.Factory()) }
        .build()
    /** 몽 정보, 몽 상태 정보 **/
    val state = StateCode.valueOf(stateCode.value)
    val mong = MongCode.valueOf(mongCode.value)

    /** 몽 **/
    Box(
        modifier = Modifier.clickable(
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
                    model = mong.gifCode,
                    imageLoader = imageLoader
                ),
                contentDescription = null, modifier = Modifier
                    .size((characterBodySize * ratio).dp)
            )
        }
        /** 표정 **/
        Box(
            modifier = Modifier.zIndex(0f)
        ) {
            val expression = when (state) {
                StateCode.CD001 -> R.drawable.sad
                StateCode.CD002 -> R.drawable.sleeping
                StateCode.CD003 -> R.drawable.depressed
                StateCode.CD004 -> R.drawable.sulky
                StateCode.CD008 -> R.drawable.eating
                StateCode.CD009 -> R.drawable.happy
                else -> R.drawable.smile
            }


            Image(
                painter = rememberAsyncImagePainter(
                    model = expression,
                    imageLoader = imageLoader),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size((characterFaceSize * ratio).dp)
            )
        }
    }
}