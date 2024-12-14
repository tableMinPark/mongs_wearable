package com.mongs.wear.presentation.pages.main.condition

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.CircularProgressIndicator
import com.mongs.wear.domain.vo.SlotVo
import com.mongs.wear.presentation.R
import com.mongs.wear.presentation.assets.MongsBlue
import com.mongs.wear.presentation.assets.MongsGreen
import com.mongs.wear.presentation.assets.MongsPink
import com.mongs.wear.presentation.assets.MongsYellow
import com.mongs.wear.presentation.component.background.MainPagerBackground
import com.mongs.wear.presentation.component.common.ProgressIndicator

@Composable
fun MainConditionView(
    slotVo: SlotVo,
    isPageChanging: State<Boolean>,
) {
    Box {
        if (!isPageChanging.value) {
            ProgressIndicator(
                progress = slotVo.exp.toFloat(),
                modifier = Modifier.zIndex(1f)
            )
        }

        MainConditionContent(
            slotVo = slotVo,
            modifier = Modifier.zIndex(1f)
        )
    }
}

@Composable
private fun MainConditionContent(
    slotVo: SlotVo,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                MainConditionCondition(
                    icon = R.drawable.health,
                    progress = slotVo.healthy.toFloat(),
                    indicatorColor = MongsPink
                )
                MainConditionCondition(
                    icon = R.drawable.satiety,
                    progress = slotVo.satiety.toFloat(),
                    indicatorColor = MongsYellow
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                MainConditionCondition(
                    icon = R.drawable.strength,
                    progress = slotVo.strength.toFloat(),
                    indicatorColor = MongsGreen
                )
                MainConditionCondition(
                    icon = R.drawable.sleep,
                    progress = slotVo.sleep.toFloat(),
                    indicatorColor = MongsBlue
                )
            }
        }
    }
}

@Composable
private fun MainConditionCondition(
    icon: Int,
    progress: Float,
    indicatorColor: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(6.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )

        CircularProgressIndicator(
            modifier = Modifier.size(60.dp),
            progress = progress / 100,
            strokeWidth = 4.dp,
            indicatorColor = indicatorColor,
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun MainConditionViewPreview() {
    Box {
        MainPagerBackground()
        ProgressIndicator(
            progress = 50f,
            modifier = Modifier.zIndex(1f)
        )
        MainConditionContent(
            slotVo = SlotVo(),
            modifier = Modifier.zIndex(1f)
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun LargeMainConditionViewPreview() {
    Box {
        MainPagerBackground()
        ProgressIndicator(
            progress = 50f,
            modifier = Modifier.zIndex(1f)
        )
        MainConditionContent(
            slotVo = SlotVo(),
            modifier = Modifier.zIndex(1f)
        )
    }
}