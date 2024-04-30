package com.paymong.wear.ui.view.slotSelect.component

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Text
import com.paymong.wear.ui.R
import com.paymong.wear.ui.global.component.StarPoint

@Composable
fun SlotBuy(
    starPoint: Int,
    slotBuyPrice: Int,
    buySlot: () -> Unit,
    context: Context = LocalContext.current
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
        ) {
            StarPoint(starPoint = starPoint)
        }

        /* 컨텐츠 */
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mong_shadow),
                    contentDescription = null,
                    modifier = Modifier
                        .zIndex(1.1f)
                        .width(80.dp)
                        .height(20.dp)
                        .align(Alignment.BottomCenter)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .zIndex(1.3f)
                        .fillMaxSize()
                        .align(Alignment.Center)
                ) {
                    Image(
                        painter = painterResource(R.drawable.starpoint_logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                    )
                    Text(
                        text = "- $slotBuyPrice",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 20.sp
                    )

                }
//                Text(
//                    text = "슬롯 추가",
//                    fontSize = 15.sp,
//                    modifier = Modifier
//                        .zIndex(1.3f)
//                        .align(Alignment.Center)
//                )
            }
        }

        /* 하단 */
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
        ) {
            SlotBuyButton(
                disable = starPoint < slotBuyPrice,
                width = 70,
                text = "구매",
                onClick = {
                    if (starPoint < slotBuyPrice) {
                        Toast.makeText(
                            context,
                            "스타 포인트 부족",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        buySlot()
                    }
                },
                modifier = Modifier
            )
        }
    }
}
