package com.paymong.wear.ui.view.main.slotAction

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paymong.wear.domain.viewModel.main.SlotActionViewModel
import com.paymong.wear.ui.R
import com.paymong.wear.ui.code.NavItem
import com.paymong.wear.ui.code.StateCode
import com.paymong.wear.ui.theme.PaymongBlue
import com.paymong.wear.ui.theme.PaymongBrown
import com.paymong.wear.ui.theme.PaymongPurple
import com.paymong.wear.ui.theme.PaymongRed
import com.paymong.wear.ui.theme.PaymongYellow

@Composable
fun SlotActionView(
    navController: NavController,
    animateSlotAction: State<Boolean>,
    hideSlotActionView: () -> Unit,
    stateCode: State<String>,
    slotActionViewModel: SlotActionViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    /** Data **/
    val state = StateCode.valueOf(stateCode.value)

    /** State **/
    val showSlotAction = remember { mutableStateOf(false) }
    if (animateSlotAction.value) {
        showSlotAction.value = true
    }

    val slotActionAlpha = animateFloatAsState(
        targetValue = if (animateSlotAction.value) 1f else 0f,
        animationSpec = tween(
            durationMillis = 350,
            delayMillis = 0
        ),
        finishedListener = {
            if (!animateSlotAction.value) {
                showSlotAction.value = false
            }
        },
        label = "showSlotAction"
    )

    /** Content **/
    if (showSlotAction.value) {
        SlotActionContent(
            alpha = slotActionAlpha.value,
            navSlotSelect = {
                navController.navigate(NavItem.SlotSelect.route)
            },
            navFeed = {
                if (state == StateCode.CD002) {
                    Toast.makeText(context, "수면 상태 입니다!", Toast.LENGTH_SHORT).show()
                } else {
                    hideSlotActionView()
                    navController.navigate(NavItem.Feed.route)
                }
            },
            setStroke = {
                if (state == StateCode.CD002) {
                    Toast.makeText(context, "수면 상태 입니다!", Toast.LENGTH_SHORT).show()
                } else {
                    hideSlotActionView()
                    slotActionViewModel.stroke()
                }
            },
            setSleep = {
                hideSlotActionView()
                slotActionViewModel.sleep()
            },
            setPoop = {
                if (state == StateCode.CD002) {
                    Toast.makeText(context, "수면 상태 입니다!", Toast.LENGTH_SHORT).show()
                } else {
                    hideSlotActionView()
                    slotActionViewModel.poop()
                }
            },
            hideSlotActionView = hideSlotActionView
        )
    }
}

@Composable
fun SlotActionContent(
    alpha: Float,
    navSlotSelect: () -> Unit,
    navFeed: () -> Unit,
    setStroke: () -> Unit,
    setSleep: () -> Unit,
    setPoop: () -> Unit,
    hideSlotActionView: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.8f))
            .alpha(alpha = alpha)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {}
            )
    ) {
        SlotActionBackButton(
            color = Color.Gray,
            onClick = hideSlotActionView
        )
        SlotActionButton(
            icon = R.drawable.ch201,
            border = PaymongBrown,
            xOffset = 0,
            yOffset = -58,
            onClick = navSlotSelect
        )
        SlotActionButton(
            icon = R.drawable.heart,
            border = PaymongRed,
            xOffset = -59,
            yOffset = -11,
            onClick = setStroke
        )
        SlotActionButton(
            icon = R.drawable.feed,
            border = PaymongYellow,
            xOffset = 59,
            yOffset = -11,
            onClick = navFeed
        )
        SlotActionButton(
            icon = R.drawable.sleep,
            border = PaymongBlue,
            xOffset = -32,
            yOffset = 50,
            onClick = setSleep
        )
        SlotActionButton(
            icon = R.drawable.poop,
            border = PaymongPurple,
            xOffset = 32,
            yOffset = 50,
            onClick = setPoop
        )
    }
}
