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
package com.example.androiddevchallenge.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.TimerViewModel
import com.example.androiddevchallenge.ui.screens.components.CompletionInfoRow
import com.example.androiddevchallenge.ui.screens.components.TimeRemaining
import com.example.androiddevchallenge.ui.screens.components.TopBar
import com.example.androiddevchallenge.ui.screens.components.TotalTimeHeader
import com.example.androiddevchallenge.ui.theme.gray

const val CIRCULAR_TIMER_RADIUS = 350f
const val TOTAL_TIME = TimerViewModel.totalTime

@Composable
fun TimerScreen(viewModel: TimerViewModel) {
    val remainingTime = viewModel.remainingTime.collectAsState().value
    val isRunning = viewModel.isRunning.collectAsState().value

    val transitionData = updateCircularTransitionData(
        remainingTime = remainingTime,
        totalTime = TOTAL_TIME
    )

    Scaffold(
        topBar = {
            TopBar(
                isRunning = isRunning,
                onStartClicked = { viewModel.onStartClicked() },
                onResetClicked = { viewModel.onResetClicked() }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            Column(modifier = Modifier) {
                TotalTimeHeader(totalTime = TOTAL_TIME)
                Spacer(modifier = Modifier.size(32.dp))

                CircularTimer(transitionData = transitionData)
                Spacer(modifier = Modifier.size(32.dp))

                CompletionInfoRow(remainingTime = remainingTime, totalTime = TOTAL_TIME)
            }

            TimeRemaining(
                timeRemaining = remainingTime,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun CircularTimer(
    transitionData: CircularTransitionData
) {
    Canvas(modifier = Modifier.fillMaxWidth().requiredHeight(CIRCULAR_TIMER_RADIUS.dp)) {
        inset(size.width / 2 - CIRCULAR_TIMER_RADIUS, size.height / 2 - CIRCULAR_TIMER_RADIUS) {
            drawCircle(
                color = gray,
                radius = CIRCULAR_TIMER_RADIUS,
                center = center,
                style = Stroke(width = 70f, cap = StrokeCap.Round)
            )

            drawArc(
                startAngle = 270f, // 270 is 0 degree
                sweepAngle = transitionData.progress,
                useCenter = false,
                color = transitionData.color,
                style = Stroke(width = 70f, cap = StrokeCap.Round)
            )
        }
    }
}
