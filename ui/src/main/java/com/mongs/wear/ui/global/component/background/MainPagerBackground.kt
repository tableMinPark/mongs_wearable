package com.mongs.wear.ui.global.component.background

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
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
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.resource.MapResourceCode
import kotlin.math.absoluteValue

private val pageBrightness = arrayOf(0.4f, 0.4f, 0.0f, 0.4f, 0.4f)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainPagerBackground(
    mapCode: String = MapResourceCode.MP000.name,
    pagerState: PagerState,
    modifier: Modifier = Modifier.zIndex(0f)
) {
    val backgroundAlpha = remember {
        derivedStateOf {
            val currentPage = pagerState.currentPage
            val ratio = pagerState.currentPageOffsetFraction.coerceIn(-1f, 1f)
            val nextPage = if (ratio < 0) {
                currentPage - 1
            } else if (ratio > 0) {
                currentPage + 1
            } else currentPage
            val current = pageBrightness[currentPage]
            val next = pageBrightness[nextPage]
            current + (next - current) * ratio.absoluteValue
        }
    }

    MainPagerBackground(
        mapCode = mapCode,
        backgroundAlpha = backgroundAlpha.value,
        modifier = modifier,
    )
}


@Composable
fun MainPagerBackground(
    mapCode: String = MapResourceCode.MP000.name,
    backgroundAlpha: Float = 0F,
    modifier: Modifier = Modifier.zIndex(0f)
) {
    val mapResourceCode = MapResourceCode.valueOf(mapCode).code
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components { add(ImageDecoderDecoder.Factory()) }
        .build()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
        ) {
            if (mapResourceCode == MapResourceCode.MP000.code) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = R.drawable.bg_gif,
                        imageLoader = imageLoader,
                        placeholder = painterResource(mapResourceCode),
                    ),
                    contentDescription = "MainPagerBackground",
                )
            } else {
                Image(
                    painter = painterResource(mapResourceCode),
                    contentDescription = "MainPagerBackground",
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = backgroundAlpha))
                    .fillMaxSize()
                    .zIndex(2f)
            )
        }
    }
}