/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimerViewModel : ViewModel() {

    private var timer: CountDownTimer? = null

    private val _remainingTime = MutableStateFlow(totalTime)
    val remainingTime: StateFlow<Long> = _remainingTime

    private var _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private fun createTimer() {
        timer = object : CountDownTimer(totalTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                _remainingTime.value = millisUntilFinished
            }

            override fun onFinish() {
                _remainingTime.value = totalTime
                _isRunning.value = false
            }
        }
    }

    fun onStartClicked() {
        createTimer()
        timer?.start()
        _isRunning.value = true
    }

    fun onResetClicked() {
        if (_isRunning.value) {
            timer?.cancel()
            _remainingTime.value = totalTime
            _isRunning.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
        _remainingTime.value = totalTime
    }

    companion object {
        const val totalTime = 60 * 1000L // 60 seconds timer, you can change it
        const val interval = 1 * 1000L
    }
}
