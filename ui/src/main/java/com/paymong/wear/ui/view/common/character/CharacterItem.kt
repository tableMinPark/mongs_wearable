package com.paymong.wear.ui.view.common.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.paymong.wear.ui.R
import com.paymong.wear.ui.code.MongCode
import com.paymong.wear.ui.code.StateCode

const val characterBodySize = 120
const val characterFaceSize = 35

@Composable
fun CharacterBody(imageLoader: ImageLoader, mong: MongCode) {
    // 몸
    Image(
        painter = rememberAsyncImagePainter(model = mong.gifCode, imageLoader = imageLoader),
        contentDescription = null, modifier = Modifier
            .size(characterBodySize.dp)
    )
}

@Composable
fun CharacterFace(imageLoader: ImageLoader, mong: MongCode, state: StateCode) {
    val faceImage = when (state) {
        StateCode.CD001 -> R.drawable.sad
        StateCode.CD002 -> R.drawable.sleeping
        StateCode.CD003 -> R.drawable.depressed
        StateCode.CD004 -> R.drawable.sulky
        StateCode.CD008 -> R.drawable.eating
        StateCode.CD009 -> R.drawable.happy
        else -> R.drawable.smile
    }
    val level = mong.code.substring(2, 3).toInt()
    val kind = mong.code.substring(3).toInt()

    val faceY = when (level) {
        1 -> 53
        2 -> if (kind == 1) 60 else 65
        3 -> if (kind == 1) 60 else 55
        else -> return
    }

    Image(
        painter = rememberAsyncImagePainter(model = faceImage, imageLoader = imageLoader),
        contentDescription = null,
        modifier = Modifier
            .size(characterFaceSize.dp)
            .offset(0.dp, (-faceY).dp)
    )
}