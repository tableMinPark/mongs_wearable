package com.mongs.wear.ui.view.battleMenu

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.mongs.wear.domain.vo.BattleRoomVo
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.component.background.BattleNestedBackground
import com.mongs.wear.ui.global.component.common.LoadingBar
import com.mongs.wear.ui.global.theme.DAL_MU_RI
import com.mongs.wear.ui.global.theme.PaymongPink200
import com.mongs.wear.ui.global.theme.PaymongWhite
import com.mongs.wear.ui.viewModel.battleMenu.BattleMenuViewModel

@Composable
fun BattleMenuView(
    navController: NavController,
    battleMenuViewModel: BattleMenuViewModel = hiltViewModel(),
) {
    val battleRoomVo = battleMenuViewModel.battleRoomVo.observeAsState(BattleRoomVo())
    Log.d("BattleMenuView", "$battleRoomVo")

    Box {
        if (battleMenuViewModel.uiState.loadingBar) {
            BattleNestedBackground()
            BattleMenuLoadingBar(modifier = Modifier.zIndex(1f))
        } else {
            BattleNestedBackground()
            BattleMenuContent(
                battle = {
                    battleMenuViewModel.uiState.loadingBar = true
                    battleMenuViewModel.matchSearch()
//                navController.navigate(NavItem.BattleMatch.route)
                },
                modifier = Modifier.zIndex(1f),
            )
        }
    }
}

@Composable
private fun BattleMenuLoadingBar(
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        LoadingBar()
    }
}


@Composable
private fun BattleMenuContent(
    battle: () -> Unit,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = battle
            ),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.25f)
            ) {
                Text(
                    text = "Mongs",
                    textAlign = TextAlign.Center,
                    fontFamily = DAL_MU_RI,
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp,
                    color = PaymongPink200,
                    maxLines = 1,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f)
            ) {
                Image(
                    painter = painterResource(R.drawable.battle_title),
                    contentDescription = null,
                    modifier = Modifier
                        .height(45.dp)
                        .width(170.dp),
                    contentScale = ContentScale.FillBounds
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.15f)
            ) {
                Text(
                    text = "터치해서 배틀하기",
                    textAlign = TextAlign.Center,
                    fontFamily = DAL_MU_RI,
                    fontWeight = FontWeight.Light,
                    fontSize = 13.sp,
                    color = PaymongPink200,
                    maxLines = 1,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
            ) {
                Image(
                    painter = painterResource(R.drawable.pointlogo),
                    contentDescription = null,
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp),
                    contentScale = ContentScale.FillBounds,
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "- 20",
                    textAlign = TextAlign.Center,
                    fontFamily = DAL_MU_RI,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp,
                    color = PaymongWhite,
                    maxLines = 1,
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun FeedMenuViewPreview() {
    Box {
        BattleNestedBackground()
        BattleMenuContent(
            battle = {},
            modifier = Modifier.zIndex(1f),
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun LargeFeedMenuViewPreview() {
    Box {
        BattleNestedBackground()
        BattleMenuContent(
            battle = {},
            modifier = Modifier.zIndex(1f),
        )
    }
}

