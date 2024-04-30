package com.paymong.wear.ui.global.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.R

@Composable
fun FeedItemDetail(
    feedItemDisable: () -> Unit,
    addWeight: Double,
    addStrength: Double,
    addSatiety: Double,
    addHealthy: Double,
    addSleep: Double,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = Color.Black.copy(alpha = 0.9f))
            .fillMaxSize()
            .zIndex(3f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = feedItemDisable,
            )
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .width(115.dp)
                    .padding(bottom = 7.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.health),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .weight(0.4f)
                )
                Text(
                    text = "+ $addHealthy",
                    modifier = Modifier.weight(0.6f),
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier
                    .width(115.dp)
                    .padding(bottom = 7.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.satiety),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .weight(0.4f)
                )
                Text(
                    text = "+ $addSatiety",
                    modifier = Modifier.weight(0.6f),
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier
                    .width(115.dp)
                    .padding(bottom = 7.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.strength),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .weight(0.4f)
                )
                Text(
                    text = "+ $addStrength",
                    modifier = Modifier.weight(0.6f),
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier
                    .width(115.dp)
                    .padding(bottom = 7.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.sleep),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .weight(0.4f)
                )
                Text(
                    text = "+ $addSleep",
                    modifier = Modifier.weight(0.6f),
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier
                    .width(115.dp)
            ) {
                Text(
                    text = "Kg",
                    modifier = Modifier.weight(0.4f)
                )
                Text(
                    text = "+ $addWeight",
                    modifier = Modifier.weight(0.6f),
                    fontSize = 20.sp
                )
            }
        }
    }
}