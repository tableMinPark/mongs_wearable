package com.paymong.wear.ui.view_.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import com.paymong.wear.ui.R

@Composable
fun MainConfigureView() {

    Box {
        MainConfigureViewContent(
            signOut = {},
            developer = {},
            setting = {},
            feedback = {}
        )
    }
}

@Composable
fun MainConfigureViewContent(
    signOut: () -> Unit,
    developer: () -> Unit,
    setting: () -> Unit,
    feedback: () -> Unit
) {
    Box(
        modifier = Modifier
            .zIndex(1f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Configure(
                    icon = R.drawable.battle,
                    border = R.drawable.interaction_bnt_pink,
                    onClick = signOut
                )
                Configure(
                    icon = R.drawable.battle,
                    border = R.drawable.interaction_bnt_pink,
                    onClick = developer
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Configure(
                    icon = R.drawable.battle,
                    border = R.drawable.interaction_bnt_pink,
                    onClick = setting
                )
                Configure(
                    icon = R.drawable.battle,
                    border = R.drawable.interaction_bnt_pink,
                    onClick = feedback
                )
            }
        }
    }
}

@Composable
fun Configure(
    icon: Int,
    border: Int,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = onClick,
        modifier = Modifier.padding(10.dp),
    ) {
        Image(
            alpha = 0.8f,
            painter = painterResource(R.drawable.interaction_bnt),
            contentDescription = null,
            modifier = Modifier.zIndex(1.1f)
        )
//        Image(
//            painter = painterResource(icon),
//            contentDescription = null,
//            modifier = Modifier.zIndex(1.2f)
//        )
//        Image(
//            painter = painterResource(border),
//            contentDescription = null,
//            modifier = Modifier.zIndex(1.2f)
//        )
    }
//    Box (
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .size(60.dp)
//            .padding(8.dp)
//            .background(
//                color = Color.Blue,
//                shape = MaterialTheme.shapes.large
//            )
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null,
//                onClick = { Log.d("test", "test") }
//            )
//    ) {
//    }
}