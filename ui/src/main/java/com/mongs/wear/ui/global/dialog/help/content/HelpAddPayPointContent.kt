package com.mongs.wear.ui.global.dialog.help.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.theme.DAL_MU_RI
import com.mongs.wear.ui.global.theme.PaymongWhite


@Composable
fun HelpAddPayPointContent() {
    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f)
        ) {
            Image(
                painter = painterResource(R.drawable.pointlogo),
                contentDescription = null,
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp),
                contentScale = ContentScale.FillBounds,
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = "는",
                textAlign = TextAlign.Center,
                fontFamily = DAL_MU_RI,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = PaymongWhite,
                maxLines = 1,
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f)
        ) {
            Image(
                painter = painterResource(R.drawable.activity),
                contentDescription = null,
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp),
                contentScale = ContentScale.FillBounds,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "훈련,",
                textAlign = TextAlign.Center,
                fontFamily = DAL_MU_RI,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = PaymongWhite,
                maxLines = 1,
            )

            Image(
                painter = painterResource(R.drawable.battle),
                contentDescription = null,
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp),
                contentScale = ContentScale.FillBounds,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "배틀,",
                textAlign = TextAlign.Center,
                fontFamily = DAL_MU_RI,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = PaymongWhite,
                maxLines = 1,
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f)
        ) {
            Text(
                text = "걷기",
                textAlign = TextAlign.Center,
                fontFamily = DAL_MU_RI,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = PaymongWhite,
                maxLines = 1,
            )
            Text(
                text = "를 통해",
                textAlign = TextAlign.Center,
                fontFamily = DAL_MU_RI,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = PaymongWhite,
                maxLines = 1,
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f)
        ) {
            Text(
                text = "획득 가능!",
                textAlign = TextAlign.Center,
                fontFamily = DAL_MU_RI,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = PaymongWhite,
                maxLines = 1,
            )
        }
        Spacer(modifier = Modifier.height(35.dp))
    }
}
