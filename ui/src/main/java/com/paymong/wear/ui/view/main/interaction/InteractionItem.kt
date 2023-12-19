package com.paymong.wear.ui.view.main.interaction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.paymong.wear.ui.R

@Composable
fun InteractionButton(
    icon: Int,
    border: Int,
    onClick: () -> Unit
) {
    Box (
        modifier = Modifier.padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick = onClick
        ) {
            Image(
                alpha = 0.8f,
                painter = painterResource(R.drawable.interaction_bnt),
                contentDescription = null
            )
            Image(
                painter = painterResource(icon),
                contentDescription = null
            )
            Image(
                painter = painterResource(border),
                contentDescription = null
            )
        }
    }
}