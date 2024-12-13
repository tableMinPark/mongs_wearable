package com.mongs.wear.presentation.pages.training.jumping

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongs.wear.domain.exception.UseCaseException
import com.mongs.wear.domain.usecase.slot.GetNowSlotUseCase
import com.mongs.wear.domain.usecase.slot.TrainingSlotUseCase
import com.mongs.wear.domain.vo.SlotVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

@HiltViewModel
class TrainingJumpingViewModel @Inject constructor(
    private val getNowSlotUseCase: GetNowSlotUseCase,
    private val trainingSlotUseCase: TrainingSlotUseCase,
) : ViewModel() {
    val uiState = UiState()

    val frame: LiveData<Int> get() = _frame
    private val _frame = MutableLiveData(0)

    val score = mutableIntStateOf(0)
    val playerEngine = mutableStateOf(PlayerEngine())
    val hurdleEngines = mutableListOf<HurdleEngine>()
    private var nowFrame = 0

    // Training
    private val maxFramePerSeconds = 40L
    private val hurdleGenerateDelay = 120
    private val gravity = 9.8f
    // Training Boundary
    private val minPy = 0f
    private val maxPy = -80f
    private val minPx = -150f
    private val maxPx = 300f
    // Point
    private val collisionPadding = 12
    // Player
    private val initPlayerSpeed = -40f
    private val initPlayerPy = PlayerEngine().py
    // Hurdle
    private val initHurdleSpeed = -3f       // 3f ~ 6f 까지
    private var hurdleSpeed = 0f


    val slotVo: LiveData<SlotVo?> get() = _slotVo
    private val _slotVo = MediatorLiveData<SlotVo?>(null)

    init {
        viewModelScope.launch (Dispatchers.Main) {
            try {
                _slotVo.addSource(
                    withContext(Dispatchers.IO) {
                        getNowSlotUseCase()
                    }
                ) { slotVo ->
                    _slotVo.value = slotVo
                }
            } catch (e: UseCaseException) {
                uiState.navMainPager = true
            }
        }
    }

    fun trainingEnd() {
        viewModelScope.launch(Dispatchers.IO) {
            trainingSlotUseCase(
                trainingCode = "JUMPING",
                score = score.intValue,
            )
            uiState.navMainPager = true
        }
    }

    fun trainingStart() {
        viewModelScope.launch(Dispatchers.Main) {
            score.intValue = 0
            playerEngine.value = PlayerEngine()
            hurdleEngines.clear()
            nowFrame = 0
            hurdleSpeed = initHurdleSpeed
            uiState.isTrainingOver = false

            while (!uiState.isTrainingOver) {
                nextFrame()
                delay(1000 / maxFramePerSeconds)
            }
        }
    }

    fun jump() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!playerEngine.value.isJump) {
                playerEngine.value.isJump = true
                playerEngine.value.speed = initPlayerSpeed
                playerEngine.value.jumpTime = 0.2f
            }
        }
    }

    private fun nextFrame() {

        if (playerEngine.value.isJump) {
            // 속도 업데이트 (중력 가속도 적용)
            playerEngine.value.speed += gravity * playerEngine.value.jumpTime
            playerEngine.value.py = min(playerEngine.value.py + playerEngine.value.speed * playerEngine.value.jumpTime, initPlayerPy)

            // 지면에 도달했는지 확인
            if (playerEngine.value.py == initPlayerPy) {
                playerEngine.value.isJump = false
                playerEngine.value.speed = 0f
                playerEngine.value.jumpTime = 0f
            }
        }

        val hurdleEngineIterator = hurdleEngines.iterator()
        while (hurdleEngineIterator.hasNext()) {
            val hurdleEngine = hurdleEngineIterator.next()
            // 장애물 이동
            hurdleEngine.px =
                max(hurdleEngine.px + hurdleSpeed, minPx)

            // 충돌 확인 -> 충돌인 경우 게임 종료
            if (isCollision(playerEngine.value, hurdleEngine)) {
                uiState.isTrainingOver = true
                uiState.trainingOverDialog = true
                break
            }

            if (!hurdleEngine.isRewarded && isUnder(playerEngine.value, hurdleEngine)) {
                hurdleEngine.isRewarded = true
                score.intValue += 1

                if (score.intValue % 5 == 0) {
                    hurdleSpeed -= 0.4f
                }
            }

            // 장애물 삭제
            if (hurdleEngine.px == minPx) {
                hurdleEngineIterator.remove()
            }
        }

        // 장애물 생성
        if (nowFrame % hurdleGenerateDelay == 0) {
            hurdleEngines.add(
                HurdleEngine(
                    height = 30,
                    width = 40,
                    px = maxPx
                )
            )
        }

        _frame.postValue(++nowFrame)
    }

    private fun isCollision (
        playerEngine: PlayerEngine,
        hurdleEngine: HurdleEngine,
    ) : Boolean {
        val playerMinY = playerEngine.py + collisionPadding
        val playerMaxY = playerEngine.py - collisionPadding + playerEngine.height
        val playerMinX = playerEngine.px + collisionPadding
        val playerMaxX = playerEngine.px - collisionPadding + playerEngine.width

        val hurdleMinY = hurdleEngine.py + collisionPadding
        val hurdleMaxY = hurdleEngine.py - collisionPadding + hurdleEngine.height
        val hurdleMinX = hurdleEngine.px + collisionPadding
        val hurdleMaxX = hurdleEngine.px - collisionPadding + hurdleEngine.width

        return (
            (hurdleMinY in playerMinY..playerMaxY && hurdleMinX in playerMinX..playerMaxX) ||
            (hurdleMaxY in playerMinY..playerMaxY && hurdleMaxX in playerMinX..playerMaxX) ||
            (hurdleMinY in playerMinY..playerMaxY && hurdleMaxX in playerMinX..playerMaxX) ||
            (hurdleMaxY in playerMinY..playerMaxY && hurdleMinX in playerMinX..playerMaxX)
        )
    }

    private fun isUnder (
        playerEngine: PlayerEngine,
        hurdleEngine: HurdleEngine,
    ) : Boolean {
        val playerMinX = playerEngine.px + collisionPadding
        val playerMaxX = playerEngine.px - collisionPadding + playerEngine.width

        val hurdleMinX = hurdleEngine.px + collisionPadding
        val hurdleMaxX = hurdleEngine.px - collisionPadding + hurdleEngine.width

        return (hurdleMinX in playerMinX..playerMaxX) || (hurdleMaxX in playerMinX..playerMaxX)
    }

    data class PlayerEngine (
        var isJump: Boolean = false,
        var jumpTime: Float = 0f,
        var speed: Float = 0f,
        val height: Int = 50,
        val width: Int = 50,
        var py: Float = 5f,
        var px: Float = 25f,
    )

    data class HurdleEngine(
        var isRewarded: Boolean = false,
        val height: Int,
        val width: Int,
        var py: Float = 0f,
        var px: Float = Float.MAX_VALUE,
    )



    class UiState (
        navMainPager: Boolean = false,
        trainingStartDialog: Boolean = true,
        isTrainingOver: Boolean = true,
        trainingOverDialog: Boolean = false,
    ) {
        var navMainPager by mutableStateOf(navMainPager)
        var trainingStartDialog by mutableStateOf(trainingStartDialog)
        var isTrainingOver by mutableStateOf(isTrainingOver)
        var trainingOverDialog by mutableStateOf(trainingOverDialog)
    }
}