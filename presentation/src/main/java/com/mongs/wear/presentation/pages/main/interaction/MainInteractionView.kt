package com.mongs.wear.presentation.pages.main.interaction

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mongs.wear.core.enums.MongStateCode
import com.mongs.wear.domain.management.vo.MongVo
import com.mongs.wear.presentation.R
import com.mongs.wear.presentation.assets.MongResourceCode
import com.mongs.wear.presentation.assets.NavItem
import com.mongs.wear.presentation.component.common.button.CircleImageButton

@Composable
fun MainInteractionView(
    navController: NavController,
    mongVo: MongVo?,
    mainInteractionViewModel: MainInteractionViewModel = hiltViewModel(),
) {
    Box {
        MainInteractionContent(
            mongVo = mongVo,
            feed = {
                navController.navigate(NavItem.FeedNested.route)
            },
            collection = {
                navController.navigate(NavItem.CollectionNested.route)
            },
            sleeping = {
                mongVo?.let {
                    mainInteractionViewModel.sleeping(mongId = mongVo.mongId)
                }
            },
            slotPick = {
                navController.navigate(NavItem.SlotPick.route)
            },
            poopClean = {
                mongVo?.let {
                    mainInteractionViewModel.poopClean(mongId = mongVo.mongId)
                }
            },
            training = {
                navController.navigate(NavItem.TrainingNested.route)
            },
            battle = {
                navController.navigate(NavItem.BattleNested.route)
            },
            modifier = Modifier.zIndex(1f)
        )
    }
}

@Composable
private fun MainInteractionContent(
    mongVo: MongVo?,
    feed: () -> Unit,
    collection: () -> Unit,
    sleeping: () -> Unit,
    slotPick: () -> Unit,
    poopClean: () -> Unit,
    training: () -> Unit,
    battle: () -> Unit,
    modifier: Modifier = Modifier.zIndex(0f),
    context: Context = LocalContext.current
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircleImageButton(
                    icon = R.drawable.feed,
                    border = R.drawable.interaction_bnt_yellow,
                    onClick = {
                        mongVo?.let {
                            if (MongResourceCode.valueOf(mongVo.mongTypeCode).isEgg) {
                                Toast.makeText(context, "알 상태에서는 불가능", Toast.LENGTH_SHORT).show()
                            } else if (mongVo.isSleeping) {
                                Toast.makeText(context, "수면 상태에서는 불가능", Toast.LENGTH_SHORT).show()
                            } else if (mongVo.stateCode == MongStateCode.DEAD) {
                                Toast.makeText(context, "죽음 상태에서는 불가능", Toast.LENGTH_SHORT).show()
                            } else {
                                feed()
                            }
                        } ?: run {
                            Toast.makeText(context, "선택 된 몽이 없음", Toast.LENGTH_SHORT).show()
                        }
                    },
                )

                Spacer(modifier = Modifier.width(8.dp))

                CircleImageButton(
                    icon = R.drawable.collection,
                    border = R.drawable.interaction_bnt_orange,
                    onClick = collection,
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircleImageButton(
                    icon = R.drawable.sleep,
                    border = R.drawable.interaction_bnt_blue,
                    onClick = {
                        mongVo?.let {
                            if (MongResourceCode.valueOf(mongVo.mongTypeCode).isEgg) {
                                Toast.makeText(context, "알 상태에서는 불가능", Toast.LENGTH_SHORT).show()
                            } else if (mongVo.stateCode == MongStateCode.DEAD) {
                                Toast.makeText(context, "죽음 상태에서는 불가능", Toast.LENGTH_SHORT).show()
                            } else {
                                sleeping()
                            }
                        } ?: run {
                            Toast.makeText(context, "선택 된 몽이 없음", Toast.LENGTH_SHORT).show()
                        }
                    },
                )

                Spacer(modifier = Modifier.width(8.dp))

                CircleImageButton(
                    icon = R.drawable.slot,
                    border = R.drawable.interaction_bnt_red,
                    onClick = slotPick,
                )

                Spacer(modifier = Modifier.width(8.dp))

                CircleImageButton(
                    icon = R.drawable.poop,
                    border = R.drawable.interaction_bnt_purple,
                    onClick = {
                        mongVo?.let {
                            if (MongResourceCode.valueOf(mongVo.mongTypeCode).isEgg) {
                                Toast.makeText(context, "알 상태에서는 불가능", Toast.LENGTH_SHORT).show()
                            } else if (mongVo.stateCode == MongStateCode.DEAD) {
                                Toast.makeText(context, "죽음 상태에서는 불가능", Toast.LENGTH_SHORT).show()
                            } else if (mongVo.isSleeping) {
                                Toast.makeText(context, "수면 상태에서는 불가능", Toast.LENGTH_SHORT).show()
                            } else {
                                poopClean()
                            }
                        } ?: run {
                            Toast.makeText(context, "선택 된 몽이 없음", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircleImageButton(
                    icon = R.drawable.activity,
                    border = R.drawable.interaction_bnt_green,
                    onClick = {
                        mongVo?.let {
                            if (MongResourceCode.valueOf(mongVo.mongTypeCode).isEgg) {
                                Toast.makeText(context, "알 상태에서는 불가능", Toast.LENGTH_SHORT).show()
                            } else if (mongVo.isSleeping) {
                                Toast.makeText(context, "수면 상태에서는 불가능", Toast.LENGTH_SHORT).show()
                            } else if (mongVo.stateCode == MongStateCode.DEAD) {
                                Toast.makeText(context, "죽음 상태에서는 불가능", Toast.LENGTH_SHORT).show()
                            } else {
                                training()
                            }
                        } ?: run {
                            Toast.makeText(context, "선택 된 몽이 없음", Toast.LENGTH_SHORT).show()
                        }
                    },
                )

                Spacer(modifier = Modifier.width(8.dp))

                CircleImageButton(
                    icon = R.drawable.battle,
                    border = R.drawable.interaction_bnt_pink,
                    onClick = {
                        mongVo?.let {
                            if (MongResourceCode.valueOf(mongVo.mongTypeCode).isEgg) {
                                Toast.makeText(context, "알 상태에서는 불가능", Toast.LENGTH_SHORT).show()
                            } else if (mongVo.isSleeping) {
                                Toast.makeText(context, "수면 상태에서는 불가능", Toast.LENGTH_SHORT).show()
                            } else if (mongVo.stateCode == MongStateCode.DEAD) {
                                Toast.makeText(context, "죽음 상태에서는 불가능", Toast.LENGTH_SHORT).show()
                            } else {
                                battle()
                            }
                        } ?: run {
                            Toast.makeText(context, "선택 된 몽이 없음", Toast.LENGTH_SHORT).show()
                        }
                    },
                )
            }
        }
    }
}
