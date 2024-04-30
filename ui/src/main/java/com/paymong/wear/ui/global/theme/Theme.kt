package com.paymong.wear.ui.global.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme

private val colors = Colors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = PaymongPink200,
    secondaryVariant = PaymongPink
)

@Composable
fun PaymongTheme(content: @Composable () -> Unit) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val typography = if (screenWidthDp < 200) SMALL else BIG

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}