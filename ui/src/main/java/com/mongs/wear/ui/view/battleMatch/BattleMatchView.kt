package com.mongs.wear.ui.view.battleMatch

import androidx.compose.foundation.Image
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
import com.mongs.wear.domain.vo.MatchPlayerVo
import com.mongs.wear.domain.vo.MatchVo
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.component.background.BattleMatchBackground
import com.mongs.wear.ui.global.component.button.BlueButton
import com.mongs.wear.ui.global.component.common.HpBar
import com.mongs.wear.ui.global.component.common.Mong
import com.mongs.wear.ui.global.resource.MongResourceCode
import com.mongs.wear.ui.global.theme.DAL_MU_RI
import com.mongs.wear.ui.global.theme.PaymongWhite
import com.mongs.wear.ui.viewModel.battleMatch.BattleMatchViewModel

@Composable
fun BattleMatchView(
    navController: NavController,
    battleMatchViewModel: BattleMatchViewModel = hiltViewModel(),
) {
    Box {
        BattleMatchBackground()
//        if (battleMatchViewModel.uiState.isMatchFind) {
//            BattleMatchFindContent(
//                otherMatchPlayerVo = ,
//                myMatchPlayerVo =
//            )
//        } else if (battleMatchViewModel.uiState.isMatchOver) {
//            BattleMatchOverContent(
//                navBattleMenu = { /*TODO*/ },
//                isWinner = ,
//                myMatchPlayerVo = MatchP
//            )
//        } else {
//            BattleMatchContent(
//                matchVo = MatchVo(),
//                otherMatchPlayerVo = MatchPlayerVo(),
//                myMatchPlayerVo = MatchPlayerVo(),
//                modifier = Modifier.zIndex(1f)
//            )
//        }
    }
}

@Composable
private fun BattleMatchFindContent(
    otherMatchPlayerVo: MatchPlayerVo,
    myMatchPlayerVo: MatchPlayerVo,
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
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f)
            ) {
                Spacer(modifier = Modifier.width(15.dp))
                Mong(
                    mong = MongResourceCode.valueOf(otherMatchPlayerVo.mongCode),
                    ratio = 0.6f
                )
                Spacer(modifier = Modifier.width(15.dp))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
            ) {
                Image(
                    painter = painterResource(R.drawable.battle),
                    contentDescription = null,
                    modifier = Modifier
                        .height(35.dp)
                        .width(45.dp),
                    contentScale = ContentScale.FillBounds
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f)
            ) {
                Spacer(modifier = Modifier.width(15.dp))
                Mong(
                    mong = MongResourceCode.valueOf(myMatchPlayerVo.mongCode),
                    ratio = 0.6f
                )
                Spacer(modifier = Modifier.width(15.dp))
                Image(
                    painter = painterResource(R.drawable.battle_me),
                    contentDescription = null,
                    modifier = Modifier
                        .height(20.dp)
                        .width(40.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}

@Composable
private fun BattleMatchContent(
    matchVo: MatchVo,
    myMatchPlayerVo: MatchPlayerVo,
    otherMatchPlayerVo: MatchPlayerVo,
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
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f)
            ) {
                Spacer(modifier = Modifier.width(15.dp))
                Mong(
                    mong = MongResourceCode.valueOf(otherMatchPlayerVo.mongCode),
                    ratio = 0.6f
                )
                Spacer(modifier = Modifier.width(15.dp))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
            ) {
                HpBar(hp = otherMatchPlayerVo.hp.toFloat())
                Text(
                    text = "${matchVo.round}/10",
                    textAlign = TextAlign.Center,
                    fontFamily = DAL_MU_RI,
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp,
                    color = PaymongWhite,
                    maxLines = 1,
                )
                HpBar(hp = myMatchPlayerVo.hp.toFloat())
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f)
            ) {
                Spacer(modifier = Modifier.width(15.dp))
                Mong(
                    mong = MongResourceCode.valueOf(myMatchPlayerVo.mongCode),
                    ratio = 0.6f
                )
                Spacer(modifier = Modifier.width(15.dp))
                Image(
                    painter = painterResource(R.drawable.battle_me),
                    contentDescription = null,
                    modifier = Modifier
                        .height(20.dp)
                        .width(40.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}

@Composable
private fun BattleMatchOverContent(
    navBattleMenu: () -> Unit,
    isWinner: Boolean,
    myMatchPlayerVo: MatchPlayerVo,
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
                    .weight(0.8f)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Mong(
                        mong = MongResourceCode.valueOf(myMatchPlayerVo.mongCode),
                        ratio = 0.8f,
                        modifier = Modifier.zIndex(1f)
                    )

                    if (isWinner) {
                        Image(
                            painter = painterResource(R.drawable.win),
                            contentDescription = null,
                            modifier = Modifier
                                .zIndex(2f)
                                .height(35.dp)
                                .width(90.dp),
                            contentScale = ContentScale.FillBounds
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.lose),
                            contentDescription = null,
                            modifier = Modifier
                                .zIndex(2f)
                                .height(35.dp)
                                .width(90.dp),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
            ) {
                BlueButton(
                    text = "배틀종료",
                    width = 100,
                    onClick = navBattleMenu
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
private fun FeedMenuViewPreview() {
    Box {
        BattleMatchBackground()
//        BattleMatchContent(
//            matchVo = MatchVo(),
//            modifier = Modifier.zIndex(1f)
//        )
//        BattleMatchFindContent(
//            matchVo = MatchVo(),
//            modifier = Modifier.zIndex(1f)
//        )
        BattleMatchOverContent(
            navBattleMenu = {},
            myMatchPlayerVo = MatchPlayerVo(),
            isWinner = false,
            modifier = Modifier.zIndex(1f)
        )
    }
}

@Preview(showSystemUi = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun LargeFeedMenuViewPreview() {
    Box {
        BattleMatchBackground()
//        BattleMatchContent(
//            matchVo = MatchVo(),
//            modifier = Modifier.zIndex(1f)
//        )
//        BattleMatchFindContent(
//            matchVo = MatchVo(),
//            modifier = Modifier.zIndex(1f)
//        )
        BattleMatchOverContent(
            navBattleMenu = {},
            myMatchPlayerVo = MatchPlayerVo(),
            isWinner = false,
            modifier = Modifier.zIndex(1f)
        )
    }
}
