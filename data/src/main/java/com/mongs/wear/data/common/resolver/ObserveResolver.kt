package com.mongs.wear.data.common.resolver

import com.mongs.wear.core.enums.MatchStateCode
import com.mongs.wear.data.activity.dto.response.CreateBattleResponseDto
import com.mongs.wear.data.activity.dto.response.FightBattleResponseDto
import com.mongs.wear.data.activity.dto.response.OverBattleResponseDto
import com.mongs.wear.data.activity.entity.MatchPlayerEntity
import com.mongs.wear.data.common.datastore.AppDataStore
import com.mongs.wear.data.common.room.RoomDB
import com.mongs.wear.data.manager.dto.response.MongObserveResponseDto
import com.mongs.wear.data.manager.dto.response.MongStateObserveResponseDto
import com.mongs.wear.data.manager.dto.response.MongStatusObserveResponseDto
import com.mongs.wear.data.user.datastore.PlayerDataStore
import com.mongs.wear.data.user.dto.response.PlayerObserveResponseDto

class ObserveResolver(
    private val roomDB: RoomDB,
    private val appDataStore: AppDataStore,
    private val playerDataStore: PlayerDataStore,
) {

    suspend fun updateSearchMatch(createBattleResponseDto: CreateBattleResponseDto) {

        val deviceId = appDataStore.getDeviceId()

        // 배틀 룸 정보 업데이트
        roomDB.matchRoomDao().let { dao ->

            dao.findByDeviceId(deviceId = deviceId)?.let { matchRoomEntity ->

                dao.save(
                    matchRoomEntity = matchRoomEntity.update(
                        roomId = createBattleResponseDto.roomId,
                        round = 0,
                        isLastRound = false,
                        stateCode = MatchStateCode.MATCH_MATCHING
                    )
                )
            }
        }

        // 배틀 플레이어 저장
        roomDB.matchPlayerDao().let { dao ->

            createBattleResponseDto.battlePlayers.forEach({ battlePlayer ->

                dao.save(
                    matchPlayerEntity = MatchPlayerEntity(
                        playerId = battlePlayer.playerId,
                        deviceId = battlePlayer.deviceId,
                        roomId = createBattleResponseDto.roomId,
                        mongTypeCode = battlePlayer.mongTypeCode,
                        hp = battlePlayer.hp,
                        roundCode = battlePlayer.roundCode,
                        isMe = battlePlayer.deviceId == deviceId,
                        isWin = false
                    )
                )
            })
        }
    }

    /**
     * 모든 플레이어 입장 완료
     */
    suspend fun updateBattleMatchEnter(fightBattleResponseDto: FightBattleResponseDto) {

        // 배틀 룸 정보 업데이트
        roomDB.matchRoomDao().let { dao ->

            dao.findByRoomId(roomId = fightBattleResponseDto.roomId)?.let { matchRoomEntity ->

                dao.save(
                    matchRoomEntity = matchRoomEntity.update(
                        round = fightBattleResponseDto.round,
                        isLastRound = fightBattleResponseDto.isLastRound,
                        stateCode = MatchStateCode.MATCH_ENTER,
                    )
                )
            }
        }

        // 배틀 플레이어 정보 업데이트
        roomDB.matchPlayerDao().let { dao ->

            fightBattleResponseDto.battlePlayers.forEach({ battlePlayer ->

                dao.findByPlayerId(playerId = battlePlayer.playerId)?.let { matchPlayerEntity ->

                    dao.save(
                        matchPlayerEntity = matchPlayerEntity.update(
                            hp = battlePlayer.hp,
                            roundCode = battlePlayer.roundCode,
                        )
                    )
                }
            })
        }
    }

    suspend fun updateBattleMatchOver(overBattleResponseDto: OverBattleResponseDto) {

        // 배틀 룸 정보 업데이트
        roomDB.matchRoomDao().let { dao ->

            dao.findByRoomId(roomId = overBattleResponseDto.roomId)?.let { matchRoomEntity ->

                dao.save(
                    matchRoomEntity = matchRoomEntity.update(
                        isLastRound = true,
                        stateCode = MatchStateCode.MATCH_OVER,
                    )
                )
            }
        }

        roomDB.matchPlayerDao().let { dao ->

            dao.findByPlayerId(playerId = overBattleResponseDto.winPlayerId)
                ?.let { matchPlayerEntity ->
                    dao.save(matchPlayerEntity = matchPlayerEntity.update(isWin = true))
                }
        }
    }

    suspend fun updateBattleMatchFight(fightBattleResponseDto: FightBattleResponseDto) {

        // 배틀 룸 정보 업데이트
        roomDB.matchRoomDao().let { dao ->

            dao.findByRoomId(roomId = fightBattleResponseDto.roomId)?.let { matchRoomEntity ->

                dao.save(
                    matchRoomEntity = matchRoomEntity.update(
                        round = fightBattleResponseDto.round,
                        isLastRound = fightBattleResponseDto.isLastRound,
                        stateCode = MatchStateCode.MATCH_FIGHT,
                    )
                )
            }
        }

        // 배틀 플레이어 정보 업데이트
        roomDB.matchPlayerDao().let { dao ->

            fightBattleResponseDto.battlePlayers.forEach({ battlePlayer ->

                dao.findByPlayerId(playerId = battlePlayer.playerId)?.let { matchPlayerEntity ->

                    dao.save(
                        matchPlayerEntity = matchPlayerEntity.update(
                            hp = battlePlayer.hp,
                            roundCode = battlePlayer.roundCode,
                        )
                    )
                }
            })
        }
    }

    suspend fun updateMong(mongObserveResponseDto: MongObserveResponseDto) {

        roomDB.mongDao().findByMongId(mongId = mongObserveResponseDto.mongId)?.let { mongEntity ->

            roomDB.mongDao().save(
                mongEntity.update(
                    mongTypeCode = mongObserveResponseDto.mongTypeCode,
                    payPoint = mongObserveResponseDto.payPoint,
                )
            )
        }
    }

    suspend fun updateMOngState(mongStateObserveResponseDto: MongStateObserveResponseDto) {

        roomDB.mongDao().findByMongId(mongId = mongStateObserveResponseDto.mongId)?.let { mongEntity ->

            roomDB.mongDao().save(
                mongEntity.update(
                    stateCode = mongStateObserveResponseDto.stateCode,
                    isSleeping = mongStateObserveResponseDto.isSleep,
                )
            )
        }
    }

    suspend fun updateMongStatus(mongStatusObserveResponseDto: MongStatusObserveResponseDto) {

        roomDB.mongDao().findByMongId(mongId = mongStatusObserveResponseDto.mongId)?.let { mongEntity ->

            roomDB.mongDao().save(
                mongEntity.update(
                    weight = mongStatusObserveResponseDto.weight,
                    expRatio = mongStatusObserveResponseDto.expRatio,
                    healthyRatio = mongStatusObserveResponseDto.healthyRatio,
                    satietyRatio = mongStatusObserveResponseDto.satietyRatio,
                    strengthRatio = mongStatusObserveResponseDto.strengthRatio,
                    fatigueRatio = mongStatusObserveResponseDto.fatigueRatio,
                    poopCount = mongStatusObserveResponseDto.poopCount,
                    statusCode = mongStatusObserveResponseDto.statusCode,
                )
            )
        }
    }

    suspend fun updatePlayer(playerObserveResponseDto: PlayerObserveResponseDto) {

        playerDataStore.setSlotCount(slotCount = playerObserveResponseDto.slotCount)

        playerDataStore.setStarPoint(starPoint = playerObserveResponseDto.starPoint)

        playerDataStore.setWalkingCount(walkingCount = playerObserveResponseDto.walkingCount)
    }
}