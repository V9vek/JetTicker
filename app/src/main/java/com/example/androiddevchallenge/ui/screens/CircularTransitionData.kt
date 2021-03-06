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

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.example.androiddevchallenge.ui.theme.green
import com.example.androiddevchallenge.ui.theme.red
import com.example.androiddevchallenge.ui.theme.yellow

class CircularTransitionData(
    progress: State<Float>,
    color: State<Color>
) {
    val progress by progress
    val color by color
}

@Composable
fun updateCircularTransitionData(
    remainingTime: Long,
    totalTime: Long
): CircularTransitionData {
    val transition = updateTransition(targetState = remainingTime)

    val progress = transition.animateFloat(
        transitionSpec = { tween(800, easing = LinearEasing) }
    ) { remTime ->
        if (remTime < 0) {
            360f
        } else {
            360f - ((360f / totalTime) * (totalTime - remTime))
        }
    }

    val color = transition.animateColor(
        transitionSpec = {
            tween(800, easing = LinearEasing)
        }
    ) {
        if (progress.value < 180f && progress.value > 90f) {
            yellow
        } else if (progress.value <= 90f) {
            red
        } else {
            green
        }
    }

    return remember(transition) { CircularTransitionData(progress = progress, color = color) }
}
