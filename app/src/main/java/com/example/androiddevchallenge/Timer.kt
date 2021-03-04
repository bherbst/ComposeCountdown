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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.ticker
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@OptIn(ExperimentalTime::class)
class Timer(val duration: Duration) {

    var remaining by mutableStateOf(duration)
    var isRunning by mutableStateOf(false)

    private lateinit var secondsChannel: ReceiveChannel<Unit>

    suspend fun start() {
        secondsChannel = ticker(
            delayMillis = 1000, // tick every second
            initialDelayMillis = 1000 // Wait a second for the first emission- otherwise we tick down immediately
        )

        isRunning = true

        for (event in secondsChannel) {
            remaining = remaining.minus(1.seconds)

            if (remaining.inSeconds <= 0) {
                secondsChannel.cancel()
            }
        }
    }

    fun pause() {
        isRunning = false
        secondsChannel.cancel()
    }
}
