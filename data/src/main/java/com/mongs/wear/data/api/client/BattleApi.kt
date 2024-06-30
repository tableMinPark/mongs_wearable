package com.mongs.wear.data.api.client

import com.mongs.wear.data.dto.battle.res.MatchWaitResDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface BattleApi {
    @POST("/battle/match/wait/{mongId}")
    suspend fun registerMatchWait(@Path("mongId") mongId: Long) : Response<MatchWaitResDto>
    @DELETE("/battle/match/wait/{mongId}")
    suspend fun removeMatchWait(@Path("mongId") mongId: Long) : Response<MatchWaitResDto>
}