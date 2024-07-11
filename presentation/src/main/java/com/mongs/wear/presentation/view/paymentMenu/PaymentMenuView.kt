package com.mongs.wear.presentation.view.paymentMenu

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.mongs.wear.presentation.R
import com.mongs.wear.presentation.global.component.background.PaymentNestedBackground
import com.mongs.wear.presentation.global.theme.DAL_MU_RI
import com.mongs.wear.presentation.global.theme.PaymongWhite

@Composable
fun PaymentMenuView(
    navController: NavController,
    context: Context = LocalContext.current
) {
    Box {
        PaymentNestedBackground()
        PaymentMenuContent(
            exchange = {
                Toast.makeText(context, "업데이트 예정", Toast.LENGTH_SHORT).show()
//                navController.navigate(NavItem.PaymentExchangePayPoint.route)
            },
            charge = {
                Toast.makeText(context, "업데이트 예정", Toast.LENGTH_SHORT).show()
//                navController.navigate(NavItem.PaymentChargeStarPoint.route)
            },
            modifier = Modifier.zIndex(1f)
        )
    }
}

@Composable
private fun PaymentMenuContent(
    exchange: () -> Unit,
    charge: () -> Unit,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.49f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = exchange
                    ),
            ) {
                Image(
                    painter = painterResource(R.drawable.pointlogo),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    contentScale = ContentScale.FillBounds,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "환전",
                    textAlign = TextAlign.Center,
                    fontFamily = DAL_MU_RI,
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp,
                    color = PaymongWhite,
                    maxLines = 1,
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(0.02f)
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .height(2.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.49f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = charge
                    ),
            ) {
                Image(
                    painter = painterResource(R.drawable.starpoint_logo),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    contentScale = ContentScale.FillBounds,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "충전",
                    textAlign = TextAlign.Center,
                    fontFamily = DAL_MU_RI,
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp,
                    color = PaymongWhite,
                    maxLines = 1,
                )
            }
        }

    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun PaymentMenuViewPreview() {
    Box {
        PaymentNestedBackground()
        PaymentMenuContent(
            exchange = {},
            charge = {},
            modifier = Modifier.zIndex(1f)
        )
    }
}