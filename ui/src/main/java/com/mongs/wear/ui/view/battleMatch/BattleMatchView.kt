package com.mongs.wear.ui.view.battleMatch

import android.util.Log
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.wear.compose.material.Text
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import com.mongs.wear.domain.code.BattlePickCode
import com.mongs.wear.domain.code.BattleStateCode
import com.mongs.wear.domain.code.MatchStateCode
import com.mongs.wear.domain.vo.MatchPlayerVo
import com.mongs.wear.domain.vo.MatchVo
import com.mongs.wear.ui.R
import com.mongs.wear.ui.global.component.background.BattleMatchBackground
import com.mongs.wear.ui.global.component.button.BlueButton
import com.mongs.wear.ui.global.component.common.HpBar
import com.mongs.wear.ui.global.component.common.LoadingBar
import com.mongs.wear.ui.global.component.common.Mong
import com.mongs.wear.ui.global.dialog.MatchPickDialog
import com.mongs.wear.ui.global.resource.MongResourceCode
import com.mongs.wear.ui.global.resource.NavItem
import com.mongs.wear.ui.global.theme.DAL_MU_RI
import com.mongs.wear.ui.global.theme.PaymongWhite
import com.mongs.wear.ui.viewModel.battleMatch.BattleMatchViewModel
import kotlinx.coroutines.delay
import kotlin.math.max
import kotlin.math.min

const val MAX_ROUND = 10
const val MAX_SECONDS = 30

@Composable
fun BattleMatchView(
    navController: NavController,
    battleMatchViewModel: BattleMatchViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    DisposableEffect(currentBackStackEntry) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                battleMatchViewModel.matchExit()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val matchVo = battleMatchViewModel.matchVo.observeAsState(MatchVo())
    val myMatchPlayerVo = battleMatchViewModel.myMatchPlayerVo.observeAsState(MatchPlayerVo())
    val otherMatchPlayerVo = battleMatchViewModel.otherMatchPlayerVo.observeAsState(MatchPlayerVo())

    Box {
        if (battleMatchViewModel.uiState.isLoading) {
            BattleMatchBackground()
            BattleMatchLoadingBar()
        } else {
            BattleMatchBackground()
            if (battleMatchViewModel.uiState.matchPickDialog) {
                MatchPickDialog(
                    maxSeconds = MAX_SECONDS,
                    attack = { battleMatchViewModel.matchPick(BattlePickCode.ATTACK) },
                    defence = { battleMatchViewModel.matchPick(BattlePickCode.DEFENCE) },
                    heal = { battleMatchViewModel.matchPick(BattlePickCode.HEAL) },
                    modifier = Modifier.zIndex(1f),
                )
            } else if (matchVo.value.matchStateCode == MatchStateCode.ENTER) {
                BattleMatchEnterContent(
                    matchStart = { battleMatchViewModel.matchStart() },
                    myMatchPlayerVo = myMatchPlayerVo.value,
                    otherMatchPlayerVo = otherMatchPlayerVo.value,
                    modifier = Modifier.zIndex(1f),
                )
            } else if (matchVo.value.matchStateCode == MatchStateCode.OVER) {
                BattleMatchOverContent(
                    navBattleMenu = { navController.popBackStack(route = NavItem.BattleNested.route, inclusive = true) },
                    myMatchPlayerVo = myMatchPlayerVo.value,
                    modifier = Modifier.zIndex(1f),
                )
            } else {
                BattleMatchContent(
                    nextRound = { battleMatchViewModel.uiState.matchPickDialog = true },
                    matchOver = { battleMatchViewModel.matchOver() },
                    matchVo = matchVo.value,
                    myMatchPlayerVo = myMatchPlayerVo.value,
                    otherMatchPlayerVo = otherMatchPlayerVo.value,
                    modifier = Modifier.zIndex(1f),
                )
            }
        }

        LaunchedEffect(Unit) {
            battleMatchViewModel.loadData()
        }
    }
}

@Composable
private fun BattleMatchLoadingBar(
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
            ) {
                LoadingBar()
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
            ) {
                Text(
                    text = "배틀 입장중",
                    textAlign = TextAlign.Center,
                    fontFamily = DAL_MU_RI,
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp,
                    color = PaymongWhite,
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
private fun BattleMatchEnterContent(
    matchStart: () -> Unit,
    myMatchPlayerVo: MatchPlayerVo,
    otherMatchPlayerVo: MatchPlayerVo,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    LaunchedEffect(Unit) {
        delay(4000)
        matchStart()
    }

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
    nextRound: () -> Unit,
    matchOver: () -> Unit,
    matchVo: MatchVo,
    myMatchPlayerVo: MatchPlayerVo,
    otherMatchPlayerVo: MatchPlayerVo,
    modifier: Modifier = Modifier.zIndex(0f),
) {
    Log.e("BattleMatchView", "$matchVo")
    if (matchVo.matchStateCode == MatchStateCode.MATCH) {
        LaunchedEffect(Unit) {
            delay(3000)
            if (matchVo.isMatchOver) {
                matchOver()
            } else {
                nextRound()
            }
        }
    }

    val nowRound = max(matchVo.round, 1)

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
                MatchPlayer(
                    matchPlayerVo = otherMatchPlayerVo,
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
                HpBar(hp = myMatchPlayerVo.hp.toFloat())
                Text(
                    text = "$nowRound/$MAX_ROUND",
                    textAlign = TextAlign.Center,
                    fontFamily = DAL_MU_RI,
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp,
                    color = PaymongWhite,
                    maxLines = 1,
                )
                HpBar(hp = otherMatchPlayerVo.hp.toFloat())
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f)
            ) {
                Spacer(modifier = Modifier.width(15.dp))
                MatchPlayer(
                    matchPlayerVo = myMatchPlayerVo,
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

                    if (myMatchPlayerVo.isWinner) {
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

@Composable
private fun MatchPlayer(
    matchPlayerVo: MatchPlayerVo,
) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components { add(ImageDecoderDecoder.Factory()) }
        .build()

    Box {
        Mong(
            mong = MongResourceCode.valueOf(matchPlayerVo.mongCode),
            ratio = 0.6f,
            modifier = Modifier.zIndex(1f)
        )

        when (matchPlayerVo.state) {
            BattleStateCode.NONE -> {}
            BattleStateCode.HEAL -> {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = R.drawable.health,
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .zIndex(2f)
                        .height(40.dp)
                        .width(40.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
            BattleStateCode.DAMAGE_AND_HEAL -> {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = R.drawable.health,
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .zIndex(2f)
                        .height(40.dp)
                        .width(40.dp),
                    contentScale = ContentScale.FillBounds
                )
                Image(
                    painter = rememberAsyncImagePainter(
                        model = R.drawable.attack_motion,
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .zIndex(3f)
                        .height(40.dp)
                        .width(40.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
            BattleStateCode.DAMAGE -> {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = R.drawable.attack_motion,
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .zIndex(2f)
                        .height(40.dp)
                        .width(40.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
            BattleStateCode.DEFENCE -> {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = R.drawable.defence_motion,
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .zIndex(2f)
                        .height(40.dp)
                        .width(40.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}