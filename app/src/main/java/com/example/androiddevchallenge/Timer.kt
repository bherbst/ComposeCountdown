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