package com.paymong.wear.ui.view.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.paymong.wear.ui.R
import com.paymong.wear.ui.code.MapCode
import com.paymong.wear.ui.code.MongCode
import com.paymong.wear.ui.code.StateCode

@Composable
fun LandingBackground() {
    Image(
        painter = painterResource(MapCode.MP000.code),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun MainBackground(
    mapCode: MapCode,
    page: Int,
    mongCode: MongCode,
    stateCode: StateCode
) {
    Image(
        painter = painterResource(mapCode.code),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
    if (mapCode == MapCode.MP000)
        MainBackgroundEffect()
    if (page != 1)
        MainBackgroundFilm()
    else if (mongCode == MongCode.CH444)
        MainBackgroundFilm()
    else if (stateCode in arrayOf(StateCode.CD005, StateCode.CD006, StateCode.CD007, StateCode.CD010 ))
        MainBackgroundFilm()
}

@Composable
fun MainBackgroundEffect() {
    val imageLoader = ImageLoader.Builder(LocalContext.current).components {add(ImageDecoderDecoder.Factory())}.build()

    Image(
        painter = rememberAsyncImagePainter(model = R.drawable.main_bg_gif, imageLoader = imageLoader),
        contentDescription = null
    )
}

@Composable
fun MainBackgroundFilm() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.4f))
    )
}