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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.CountdownTheme
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.minutes

@OptIn(ExperimentalTime::class)
@Composable
fun CountdownScreen(
    defaultDuration: Duration = 2.minutes
) {
    var timer by remember { mutableStateOf(Timer(defaultDuration)) }
    val scope = rememberCoroutineScope()

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier)
            Countdown(
                duration = timer.duration,
                remaining = timer.remaining,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier)

            TimerControls(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onPlayPause = {
                    if (timer.isRunning) {
                        timer.pause()
                    } else {
                        scope.launch { timer.start() }
                    }
                },
                isRunning = timer.isRunning,
                onEdit = {
                    // TODO real implemenation
                    timer = Timer(defaultDuration)
                },
            )
        }
    }
}

@Composable
private fun TimerControls(
    onPlayPause: () -> Unit,
    isRunning: Boolean,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FloatingActionButton(onClick = onPlayPause) {
            Icon(
                imageVector = if (isRunning) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = if (isRunning) "Pause" else "Play"
            )
        }

        FloatingActionButton(onClick = onEdit) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Pause"
            )
        }
    }
}

@Preview
@Composable
private fun CountdownScreenPreview() {
    CountdownTheme {
        CountdownScreen()
    }
}
