package com.paymong.wear.domain.viewModel.training

import android.graphics.Rect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class TrainingJumpingViewModel @Inject constructor(

) : ViewModel() {
    val score: LiveData<Int> get() = _score
    private val _score = MutableLiveData(0)

    val character: LiveData<CharacterState> get() = _character
    private val _character = MutableLiveData(CharacterState())
    val hurdle: LiveData<HurdleState> get() = _hurdle
    private val _hurdle: MutableLiveData<HurdleState> = MutableLiveData(HurdleState())

    private var iSGameOver = false
    private var time = 0

    init {
        nextFrame()
    }

    private fun nextFrame() {
        viewModelScope.launch(Dispatchers.IO) {
            while(!iSGameOver) {
                delay(10)
                _hurdle.value?.nextFrame()
                _character.value?.nextFrame()
                _score.postValue(time++)
            }
        }
    }
}

data class HurdleState(
    val hurdleList: ArrayList<Hurdle> = ArrayList(),
    val speed: Int = 1
) {
    private val max = 1
    private val startX = 200

    init {
        hurdleList.clear()
        for (i in 0 until max) {
            generate()
        }
    }

    fun nextFrame() {
        hurdleList.forEach {
            it.move()
        }
        if (hurdleList.first().x < -100) {
            hurdleList.removeFirst()
            generate()
        }
    }

    private fun generate() {
        val random = rand(100, 200)
        hurdleList.add(Hurdle(x = startX + random, y = 0))
    }
    private fun rand(from: Int, to: Int) : Int {
        return java.util.Random().nextInt(to - from) + from
    }

}


data class Hurdle(
    var x: Int = 200,
    var y: Int = 0,
) {
    fun move() {
        x -= 1
    }
}

data class CharacterState(
    var x: Int = 20,
    var y: Int = 0,
    var isJumping: Boolean = false
) {
    fun nextFrame() {
        if (y == -80) {
            isJumping = false
        }
        if (isJumping) {
            y -= 1
        } else if(y < 0) {
            y += 1
        }
    }
}