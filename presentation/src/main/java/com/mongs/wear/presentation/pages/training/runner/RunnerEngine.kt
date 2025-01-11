package com.mongs.wear.presentation.pages.training.runner

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.min

class RunnerEngine(
    private val defaultY: Float,
) {

    companion object {
        private const val TAG = "RunnerEngine"

        private const val GRAVITY = 9.8f
        private const val FRAME = 30L
        private const val COLLISION_PADDING = 15
        private const val HURDLE_GENERATE_DELAY_MILLIS = 5000

        private const val PLAYER_SPEED = 40f
    }

    private val _endEvent = MutableSharedFlow<Unit>()
    val endEvent = _endEvent.asSharedFlow()

    val playMillis: MutableState<Long> = mutableLongStateOf(0L)
    val isStartGame: MutableState<Boolean> = mutableStateOf(false)
    val score: MutableState<Int> = mutableIntStateOf(0)
    val player: MutableState<Player?> = mutableStateOf(null)
    val hurdleList: MutableList<Hurdle> = mutableListOf()

    /**
     * 시작
     */
    fun start(height: Int, width: Int) {
        CoroutineScope(Dispatchers.IO).launch {

            var generateHurdleCount = 0L
            playMillis.value = 0L
            isStartGame.value = true
            score.value = 0

            // 플레이어 생성
            player.value = Player(
                height = height,
                width = width,
                sy = defaultY,
                sx = 25f,
                ss = PLAYER_SPEED,
                sf = 0.2f,
            )

            while (isStartGame.value) {

                val processStart = LocalDateTime.now()

                // 게임 시간 증가
                playMillis.value += 1000L / FRAME

                // 좌표 변경 및 충돌 확인
                player.value?.let { player ->
                    // 플레이어 이동
                    player.move()

                    // 장애물 이동
                    val hurdleIterator = hurdleList.iterator()

                    while(hurdleIterator.hasNext()) {

                        val hurdle = hurdleIterator.next()

                        hurdle.move()

                        // 장애물 충돌 확인
                        if (isCollision(player = player, hurdle = hurdle)) {
                            isStartGame.value = false
                        }
                        // 장애물 넘음 확인
                        else if (isUnder(player = player, hurdle = hurdle) && !hurdle.isContainedScore) {
                            hurdle.isContainedScore = true
                            score.value++
                        }

                        // 지나가 장애물 삭제
                        if (hurdle.px.value < -350f) {
                            hurdleIterator.remove()
                        }
                    }

                    // 장애물 생성
                    if (playMillis.value / HURDLE_GENERATE_DELAY_MILLIS > generateHurdleCount) {
                        generateHurdle()
                        generateHurdleCount = playMillis.value / HURDLE_GENERATE_DELAY_MILLIS
                    }
                }

                // 좌표 변경 로직 시간 측정
                val processMillis = Duration.between(processStart, LocalDateTime.now()).toMillis()

                // 좌표 변경 로직 시간 제외한 딜레이 진행
                delay(Math.max(0, 1000 / FRAME - processMillis))
            }

            _endEvent.emit(Unit)
        }
    }

    /**
     * 장애물 생성
     */
    private fun generateHurdle() {
        hurdleList.add(
            Hurdle(
                height = 30,
                width = 40,
                sy = defaultY,
                sx = 350f,
                ss = 3f
            )
        )
    }

    /**
     * 종료
     */
    fun end() {
        isStartGame.value = false
    }

    /**
     * 충돌 여부 확인
     * TODO: 확인 필요
     */
    private fun isCollision(player: Player, hurdle: Hurdle) : Boolean {

        val playerMinY = player.py.value + COLLISION_PADDING
        val playerMaxY = player.py.value - COLLISION_PADDING + player.height
        val playerMinX = player.px.value + COLLISION_PADDING
        val playerMaxX = player.px.value - COLLISION_PADDING + player.width

        val hurdleMinY = hurdle.py.value + COLLISION_PADDING
        val hurdleMaxY = hurdle.py.value - COLLISION_PADDING + hurdle.height
        val hurdleMinX = hurdle.px.value + COLLISION_PADDING
        val hurdleMaxX = hurdle.px.value - COLLISION_PADDING + hurdle.width

        return (
            (hurdleMinY in playerMinY..playerMaxY && hurdleMinX in playerMinX..playerMaxX) ||
            (hurdleMaxY in playerMinY..playerMaxY && hurdleMaxX in playerMinX..playerMaxX) ||
            (hurdleMinY in playerMinY..playerMaxY && hurdleMaxX in playerMinX..playerMaxX) ||
            (hurdleMaxY in playerMinY..playerMaxY && hurdleMinX in playerMinX..playerMaxX)
        )
    }

    /**
     * 장애물 아래 여부 확인
     */
    private fun isUnder(player: Player, hurdle: Hurdle) : Boolean {

        val playerMinX = player.px.value + COLLISION_PADDING
        val playerMaxX = player.px.value - COLLISION_PADDING + player.width

        val hurdleMinX = hurdle.px.value + COLLISION_PADDING
        val hurdleMaxX = hurdle.px.value - COLLISION_PADDING + hurdle.width

        return (hurdleMinX in playerMinX..playerMaxX) || (hurdleMaxX in playerMinX..playerMaxX)
    }

    class Player(
        private val sy: Float,
        private val sx: Float,
        private val ss: Float,
        private val sf: Float,
        private var jumpSpeed: Float = ss,
        private var jumpFrame: Float = sf,
        private var isJump: Boolean = false,
        val height: Int,
        val width: Int,
        var py: MutableState<Float> = mutableFloatStateOf(sy),
        var px: MutableState<Float> = mutableFloatStateOf(sx),
    ) {

        /**
         * 점프
         */
        fun jump() {
            if (!isJump) {
                this.isJump = true
                this.py.value = this.sy
                this.jumpSpeed = this.ss
                this.jumpFrame = this.sf
            }
        }

        /**
         * 이동
         */
        fun move() {
            if (isJump) {
                // 스피드 변경
                this.jumpSpeed -= GRAVITY * this.jumpFrame

                // 좌표 변경
                val nextPy = this.py.value - this.jumpSpeed * this.jumpFrame
                this.py.value = min(nextPy, this.sy)

                // 지면 도착 여부 확인
                if (this.py.value == this.sy) {
                    this.isJump = false
                    this.jumpSpeed = 0f
                    this.jumpFrame = 0f
                }
            }
        }
    }

    class Hurdle(
        private val sy: Float,
        private val sx: Float,
        private val ss: Float,
        private var speed: Float = ss,
        val height: Int,
        val width: Int,
        var py: MutableState<Float> = mutableFloatStateOf(sy),
        var px: MutableState<Float> = mutableFloatStateOf(sx),
        var isContainedScore: Boolean = false,
    ) {
        fun move() {
            px.value -= this.speed
        }
    }
}