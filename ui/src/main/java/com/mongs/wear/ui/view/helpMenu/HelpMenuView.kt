package com.mongs.wear.ui.view.helpMenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Text
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.component.background.HelpNestedBackground
import com.mongs.wear.ui.global.component.common.Chip
import com.mongs.wear.ui.global.dialog.help.HelpBattleDialog
import com.mongs.wear.ui.global.dialog.help.HelpMongDialog
import com.mongs.wear.ui.global.dialog.help.HelpPointDialog
import com.mongs.wear.ui.global.theme.DAL_MU_RI
import com.mongs.wear.ui.global.theme.PaymongWhite
import com.mongs.wear.ui.viewModel.helpMenu.HelpMenuViewModel

@Composable
fun HelpMenuView(
    helpMenuViewModel: HelpMenuViewModel = hiltViewModel()
) {

    Box {
        HelpNestedBackground()
        HelpMenuContent(
            helpPointDialog = { helpMenuViewModel.uiState.helpPointDialog = true },
            helpMongDialog = { helpMenuViewModel.uiState.helpMongDialog = true },
            helpBattleDialog = { helpMenuViewModel.uiState.helpBattleDialog = true },
            modifier = Modifier.zIndex(1f)
        )
        if (helpMenuViewModel.uiState.helpPointDialog) {
            HelpPointDialog(
                cancel = { helpMenuViewModel.uiState.helpPointDialog = false },
                modifier = Modifier.zIndex(2f),
            )
        } else if (helpMenuViewModel.uiState.helpMongDialog) {
            HelpMongDialog(
                cancel = { helpMenuViewModel.uiState.helpPointDialog = false },
                modifier = Modifier.zIndex(2f),
            )
        } else if (helpMenuViewModel.uiState.helpBattleDialog) {
            HelpBattleDialog(
                cancel = { helpMenuViewModel.uiState.helpPointDialog = false },
                modifier = Modifier.zIndex(2f),
            )
        }
    }
}

@Composable
private fun HelpMenuContent(
    helpPointDialog: () -> Unit,
    helpMongDialog: () -> Unit,
    helpBattleDialog: () -> Unit,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    val listState = rememberScalingLazyListState(initialCenterItemIndex = 1)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        PositionIndicator(scalingLazyListState = listState)
        ScalingLazyColumn(
            contentPadding = PaddingValues(vertical = 60.dp, horizontal = 6.dp),
            modifier = Modifier.fillMaxSize(),
            state = listState,
            autoCentering = null,
        ) {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Text(
                        text = "도움말",
                        textAlign = TextAlign.Center,
                        fontFamily = DAL_MU_RI,
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp,
                        color = PaymongWhite,
                        maxLines = 1,
                    )
                }
            }

            item {
                Chip(
                    fontColor = Color.White,
                    backgroundColor = Color.Black,
                    label = "포인트",
                    secondaryLabel = "페이/스타 포인트 획득 방법",
                    onClick = helpPointDialog,
                )
            }

            item {
                Chip(
                    fontColor = Color.White,
                    backgroundColor = Color.Black,
                    label = "캐릭터",
                    secondaryLabel = "육성/진화방법,사망조건",
                    onClick = helpMongDialog,
                )
            }

            item {
                Chip(
                    fontColor = Color.White,
                    backgroundColor = Color.Black,
                    label = "배틀",
                    secondaryLabel = "매칭,매치,승리보상",
                    onClick = helpBattleDialog,
                )
            }
        }
    }
}