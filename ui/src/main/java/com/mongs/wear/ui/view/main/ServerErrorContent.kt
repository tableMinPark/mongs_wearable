package com.mongs.wear.ui.view.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Text
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.component.button.BlueButton
import com.mongs.wear.ui.global.theme.DAL_MU_RI
import com.mongs.wear.ui.global.theme.PaymongWhite


@Composable
fun ServerErrorContent (
    closeApp: () -> Unit,
    modifier: Modifier = Modifier.zIndex(0f)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Image(
                painter = painterResource(R.drawable.logo_not_open),
                contentDescription = null,
                modifier = Modifier.size(55.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "서버연결실패",
                textAlign = TextAlign.Center,
                fontFamily = DAL_MU_RI,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                color = PaymongWhite,
            )

            Spacer(modifier = Modifier.height(25.dp))

            BlueButton(
                text =  "앱종료",
                onClick = closeApp,
            )
        }
    }
}
