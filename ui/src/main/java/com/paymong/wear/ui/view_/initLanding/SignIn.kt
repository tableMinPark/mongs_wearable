package com.paymong.wear.ui.view_.initLanding

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymong.wear.ui.R

@Composable
fun SignIn(
    width: Int,
    onClick: () -> Unit
) {
    val googleSignIn = painterResource(R.drawable.google_login)

    Box {
        Image(
            painter = googleSignIn,
            contentDescription = null,
            modifier = Modifier
                .width(width = width.dp)
                .clickable (
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                )
        )
    }
}