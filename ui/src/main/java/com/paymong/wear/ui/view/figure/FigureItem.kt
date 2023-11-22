package com.paymong.wear.ui.view.figure

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.wear.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import com.paymong.wear.ui.R
import com.paymong.wear.ui.code.MongCode
import com.paymong.wear.ui.code.StateCode

const val characterBodySize = 120
const val characterFaceSize = 35
const val poopSize = 25
const val heartSize = 17

@Composable
fun Character(stateCode: StateCode, mongCode: MongCode, stroke: () -> Unit) {
    val imageLoader = ImageLoader.Builder(LocalContext.current).components {add(ImageDecoderDecoder.Factory())}.build()

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        CharacterBody(imageLoader, mongCode, stroke)
        CharacterFace(imageLoader, mongCode, stateCode)
    }
}

@Composable
fun CharacterBody(imageLoader: ImageLoader, mongCode: MongCode, stroke: () -> Unit) {
    // 몸
    Image(
        painter = rememberAsyncImagePainter(model = mongCode.gifCode, imageLoader = imageLoader),
            contentDescription = null, modifier = Modifier
            .size(characterBodySize.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = stroke
            )
    )

}

@Composable
fun CharacterFace(imageLoader: ImageLoader, mongCode: MongCode, stateCode: StateCode) {
    val faceImage = when (stateCode) {
        StateCode.CD001 -> R.drawable.sad
        StateCode.CD002 -> R.drawable.sleeping
        StateCode.CD003 -> R.drawable.depressed
        StateCode.CD004 -> R.drawable.sulky
        StateCode.CD008 -> R.drawable.eating
        StateCode.CD009 -> R.drawable.happy
        else -> R.drawable.smile
    }
    val level = mongCode.code.substring(2, 3).toInt()
    val kind = mongCode.code.substring(3).toInt()

    val facePadding = when (level) {
        // level 1
        1 -> PaddingValues(0.dp, 0.dp, 0.dp, 53.dp)
        // level 2
        2 -> if (kind == 1) PaddingValues(0.dp, 0.dp, 0.dp, 60.dp)
            else PaddingValues(0.dp, 0.dp, 0.dp, 65.dp)
        // level 3
        3 -> if (kind == 1) PaddingValues(0.dp, 0.dp, 0.dp, 60.dp)
            else PaddingValues(0.dp, 0.dp, 0.dp, 55.dp)
        else -> return
    }

    Image(
        painter = rememberAsyncImagePainter(model = faceImage, imageLoader = imageLoader),
        contentDescription = null,
        modifier = Modifier
            .padding(facePadding)
            .size(characterFaceSize.dp)
    )
}

@Composable
fun CharacterEmpty() {
    val text = "스마트폰에서\n알을 생성해주세요."
    Text(text = text, style = MaterialTheme.typography.body1)
}

@Composable
fun CharacterDead() {

}

@Composable
fun CharacterGraduation() {

}

@Composable
fun CharacterEvolutionReady() {

}

@Composable
fun CharacterEvolution() {

}

@Composable
fun Poop(poopCount: Int) {
    val poopPadding = arrayOf(
        arrayOf(-40, -10),
        arrayOf(38, -12),
        arrayOf(-30, -2),
        arrayOf(27, 0),
    )
    val poop = painterResource(R.drawable.poops)
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Box() {

        }
        for (count in 1..poopCount) {
            val idx = count - 1
            Image(
                painter = poop,
                modifier = Modifier
                    .offset(poopPadding[idx][0].dp, poopPadding[idx][1].dp)
                    .size(poopSize.dp),
                contentDescription = null
            )
        }
    }
}

@Composable
fun Heart() {
    val heart = painterResource(R.drawable.heart)
    Box(
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Image(
            painter = heart,
            modifier = Modifier.size(heartSize.dp),
            contentDescription = null
        )
    }
}
