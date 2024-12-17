package com.mongs.wear.domain.client

import com.mongs.wear.core.enums.MatchRoundCode

interface MqttClient {

    /**
     * 구독 준비 (콜백 클래스 등록 및 MqttClient 객체 생성)
     */
    suspend fun connect()

    /**
     * 유지된 구독 정보로 전체 구독
     */
    suspend fun resumeConnect()

    /**
     * 구독중인 topic 전체 구독 해제
     * 구독 정보 유지 (mongId, accountId, roomId)
     */
    suspend fun pauseConnect()

    /**
     * 구독중인 topic 전체 구독 해제
     * 구독 정보 삭제 (mongId, accountId, roomId)
     */
    suspend fun disconnect()

    /**
     * Manager 구독
     */
    suspend fun subManager(mongId :Long)

    suspend fun resumeManager()

    suspend fun pauseManager()

    /**
     * Manager 구독 해제
     * 구독 정보 삭제 (mongId)
     */
    suspend fun disSubManager()

    /**
     * SearchMatch 구독
     */
    suspend fun subSearchMatch(deviceId: String)

    suspend fun resumeSearchMatch()

    suspend fun pauseSearchMatch()

    /**
     * SearchMatch 구독 해제
     * 구독 정보 삭제 (deviceId)
     */
    suspend fun disSubSearchMatch()

    /**
     * BattleMatch 구독
     */
    suspend fun subBattleMatch(roomId: Long)

    /**
     * BattleMatch 입장
     */
    suspend fun pubBattleMatchEnter(roomId: Long, playerId: String)

    /**
     * BattleMatch 선택
     */
    suspend fun pubBattleMatchPick(roomId: Long, playerId: String, targetPlayerId: String, pickCode: MatchRoundCode)

    /**
     * BattleMatch 퇴장
     */
    suspend fun pubBattleMatchExit(roomId: Long, playerId: String)

    suspend fun resumeBattleMatch()

    suspend fun pauseBattleMatch()

    /**
     * BattleMatch 구독 해제
     * 구독 정보 삭제 (roomId)
     */
    suspend fun disSubBattleMatch()

    /**
     * Player 구독
     */
    suspend fun subPlayer(accountId: Long)

    suspend fun resumePlayer()

    suspend fun pausePlayer()

    /**
     * Player 구독 해제
     * 구독 정보 삭제 (accountId)
     */
    suspend fun disSubPlayer()
}