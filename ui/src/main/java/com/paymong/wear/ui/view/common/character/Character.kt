package com.paymong.wear.ui.view.common.character

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.decode.ImageDecoderDecoder
import com.paymong.wear.ui.code.MongCode
import com.paymong.wear.ui.code.StateCode

@Composable
fun Character(mong: MongCode) {
    Character(
        state = StateCode.CD000,
        mong = mong,
        showSlotActionView = {}
    )
}

@Composable
fun Character(state: StateCode, mong: MongCode, showSlotActionView: () -> Unit) {
    val imageLoader =
        ImageLoader.Builder(LocalContext.current).components { add(ImageDecoderDecoder.Factory()) }
            .build()

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = showSlotActionView
            )
    ) {
        CharacterBody(imageLoader, mong)
        CharacterFace(imageLoader, mong, state)
    }
}
