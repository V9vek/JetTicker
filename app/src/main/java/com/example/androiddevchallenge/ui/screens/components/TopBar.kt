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
package com.example.androiddevchallenge.ui.screens.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.ReplayCircleFilled
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.purple

@Composable
fun TopBar(
    isRunning: Boolean,
    onStartClicked: () -> Unit,
    onResetClicked: () -> Unit
) {
    TopAppBar(elevation = 0.dp) {
        Box(modifier = Modifier.fillMaxSize()) {
            RotatingImage(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(top = 4.dp, start = 16.dp)
            )
            Text(
                text = "T I M E R",
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.Center).padding(4.dp)
            )
            IconButton(
                onClick = if (isRunning) onResetClicked else onStartClicked,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .size(40.dp)
            ) {
                Icon(
                    imageVector = if (isRunning) Icons.Default.ReplayCircleFilled else Icons.Default.PlayCircle,
                    contentDescription = "ToggleTimer",
                    tint = purple,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun RotatingImage(modifier: Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val angleState by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing)
        )
    )

    // you can add different px density image for different devices
    val image = ImageBitmap.imageResource(id = R.drawable.glassclock)
    val imageWidth = image.width
    val imageHeight = image.height

    Canvas(modifier = modifier.size(imageWidth.dp, imageHeight.dp)) {
        rotate(
            degrees = 360 * angleState,
            pivot = Offset(x = imageWidth.toFloat() / 2, y = imageHeight.toFloat() / 2)
        ) {
            drawImage(image = image)
        }
    }
}
