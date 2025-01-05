package com.mongs.wear.data.user.consumer

import com.google.gson.Gson
import com.mongs.wear.data.user.dto.response.PlayerObserveResponseDto
import com.mongs.wear.data.user.resolver.PlayerObserveResolver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerConsumer @Inject constructor(
    private val observeResolver: PlayerObserveResolver,
    private val gson: Gson,
) {

    companion object {
        private const val TAG = "PlayerConsumer"
        private const val USER_PLAYER_OBSERVE_MEMBER = "USER-PLAYER-000"
    }

    fun messageArrived(code: String, resultJson: String) {
        when (code) {

            USER_PLAYER_OBSERVE_MEMBER ->
                observeResolver.updatePlayer(
                    playerObserveResponseDto = gson.fromJson(
                        resultJson,
                        PlayerObserveResponseDto::class.java
                    )
                )

            else -> {}
        }
    }
}