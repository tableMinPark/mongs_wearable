package com.paymong.wear.ui.view.common.background

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import com.paymong.wear.ui.R
import com.paymong.wear.ui.code.MapCode

@Composable
fun LandingBackground() {
    Image(
        painter = painterResource(MapCode.MP000.code),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun Background(
    map: MapCode
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(map.code),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        if (map == MapCode.MP000) {
            BackgroundEffect()
        }
    }
}

@Composable
fun FeedBackGround() {
    Image(
        painter = painterResource(MapCode.MP000.code),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun FeedSelectBackGround() {
    Image(
        painter = painterResource(MapCode.MP000.code),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
    FilmBackground()
}

@Composable
fun SlotSelectBackground() {
    Image(
        painter = painterResource(MapCode.MP000.code),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
    FilmBackground()
}

@Composable
fun BackgroundEffect() {
    val imageLoader = ImageLoader.Builder(LocalContext.current).components {add(ImageDecoderDecoder.Factory())}.build()

    Image(
        painter = rememberAsyncImagePainter(
            model = R.drawable.main_bg_gif,
            imageLoader = imageLoader
        ),
        contentDescription = null
    )
}

@Composable
fun FilmBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.4f))
    )
}